import requests
from bs4 import BeautifulSoup
from pymongo import MongoClient 
import multiprocessing
import time
import datetime



def multiprocessingScraping(ano):
    categoriasUnicas=[]
    docAno=requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)).text
    soup = BeautifulSoup(docAno,'html5lib')

    for event in soup.select("select#event > option"):
        tituloCarrera=event.get("title").split(" - ",1)[0]
        abreviaturaCarrera=event.get("value");
        #print(tituloCarrera)
        #print(abreviaturaCarrera)
    
        docAnoAbreviatura = requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)).text; 
        soup2=BeautifulSoup(docAnoAbreviatura,'html5lib')
            
        for category in soup2.select("select#category > option"):
            categoria=category.get("value")
            if categoria in categoriasUnicas:
                pass
            else:
                categoriasUnicas.append(categoria)
            print(categoriasUnicas)

            docAnoAbreviaturaCategoria = requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)+"/"+str(categoria)).text;  
            soup3=BeautifulSoup(docAnoAbreviaturaCategoria,'html5lib')
            for session in soup3.select("select#session > option"):

                abreviaturaSesion=session.get("value")
                if abreviaturaSesion=="RAC":
                    tituloSesion=session.get("title")
                    #print(abreviaturaSesion)
                    #print(tituloSesion)

                        

                    docAnoAbreviaturaCategoriaSesion = requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)+"/"+str(categoria)+"/"+str(abreviaturaSesion)).text;  
                    soup4=BeautifulSoup(docAnoAbreviaturaCategoriaSesion,'html5lib')
                    print("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)+"/"+str(categoria)+"/"+str(abreviaturaSesion))

                        
                    arrayPDF=[]
                    for test in soup4.select('a'):
                        if (test.has_attr('href')):
                            if ("pdf" in str(test)):                             
                                arrayPDF.append(test.get("href"))

                    try:
                        sitioYFecha=soup4.find('p',{"class":"padbot5"}).text
                        sitioEvento=sitioYFecha.split(",",1)[0]  
                        fechaEvento=sitioYFecha.split(",",1)[1]
                        fecha_parsed=datetime.datetime.strptime(fechaEvento,' %A, %B %d, %Y')
                    except:
                        fechaEvento=""
                        fecha_parsed=""
                        
                        
                    for tablaCarrera in soup4.select("table.width100.marginbot10.fonts12"):
                        for tr in tablaCarrera.select("tr")[1:]:
                            #Aquí deberiamos comprobar que existen entradas en la tabla.
                            tds=tr.select("td")
                            
                            try:    
                                piloto=tds[3].text
                                pais=tds[4].text
                                equipo=tds[5].text  
                                moto=tds[6].text  
                                tiempoDiferencia=tds[8].text
                                try:
                                    pos=int(tds[0].text)                                        
                                except:
                                    pos=0
                                try:
                                    puntos=int(tds[1].text)
                                except:
                                    puntos=0
                                try:
                                    numero=int(tds[2].text)
                                except:
                                    numero=0
                                try:
                                    kmh=float(tds[7].text)
                                except:
                                    kmh=0 

                                collection.insert_one({"temporada":int(ano),"categoria":categoria,"abreviatura":abreviaturaCarrera,"titulo":tituloCarrera,"lugar":sitioEvento,"fecha":fecha_parsed, 'pos':pos,'puntos':puntos,'num':numero,'piloto':piloto,'pais':pais,'equipo':equipo,'moto':moto,'kmh':kmh,'diferencia':tiempoDiferencia})
                                    
                                    
                            except:
                                pass
                       
                    diccionarioDocumentacion={"temporada":int(ano),"categoria":categoria,"abreviatura":abreviaturaCarrera,"titulo":tituloCarrera,"lugar":sitioEvento,"fecha":fechaEvento,"documentacion":arrayPDF}
                    collection3.insert_one(diccionarioDocumentacion)

          
    campeonatos = soup.find('div', id='champ_results')
    cont=0
    for li in campeonatos.find_all('li'):
        
           
        docCampeonato=requests.get("http://www.motogp.com"+li.find('a').get('href')).text
        soupCampeonato = BeautifulSoup(docCampeonato,'html5lib')
        tablaCampeonato=soupCampeonato.find("table", {"class":"width100"})


        for tr in tablaCampeonato.select("tr")[1:]:
            #Aquí deberiamos comprobar que existen entradas en la tabla.
            tds=tr.select("td")   
                
            piloto=tds[1].text
            moto=tds[2].text  
            pais=tds[3].text
                
            try:
                pos=int(tds[0].text)                                        
            except:
                pos=0
            try:
                puntos=int(tds[4].text)
            except:
                puntos=0

            collection2.insert_one({"temporada":int(ano),"categoria":categoriasUnicas[cont],'pos':pos,'piloto':piloto,'moto':moto,'pais':pais,'puntos':puntos})

        cont=cont+1

try: 
    global conn 
    conn = MongoClient() 
    print("Connected successfully on Process:"+ multiprocessing.current_process().name)
except:   
    print("Could not connect to MongoDB")

# database 

db = conn.motoGP_db
global collection 
collection = db.carreras 

global collection2
collection2= db.campeonatos

global collection3
collection3= db.documentacion
doc= requests.get("http://www.motogp.com/es/Results+Statistics/").text
soup0=BeautifulSoup(doc,'html5lib')
starttime = time.time()
procesos = []


if __name__ == '__main__':
    print("comprobacion paralela")
    db.carreras.drop()
    db.campeonatos.drop()
    db.documentacion.drop()
    for ano in range(1949,2018):       
        proceso = multiprocessing.Process(target=multiprocessingScraping, args=(ano,))
        procesos.append(proceso)
        proceso.start()
        
    for proceso in procesos:
        proceso.join()
        
    print('That took {} seconds'.format(time.time() - starttime))

