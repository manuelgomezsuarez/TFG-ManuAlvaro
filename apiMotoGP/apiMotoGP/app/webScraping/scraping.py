import requests
from bs4 import BeautifulSoup
from pymongo import MongoClient 


try: 
    conn = MongoClient() 
    print("Connected successfully!!!") 
except:   
    print("Could not connect to MongoDB")

# database 
db = conn.motoGP_db
db.carreras.drop()
db.campeonatos.drop()
db.documentacion.drop()
collection = db.carreras 
collection2= db.campeonatos
collection3= db.documentacion





doc= requests.get("http://www.motogp.com/es/Results+Statistics/").text
soup0=BeautifulSoup(doc,'html5lib')
for something in soup0.select("select#season > option"):
    ano=something.get("value")
    if int(ano) == 2017:
        #break
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

                        sitioYFecha=soup4.find('p',{"class":"padbot5"}).text
                        sitioEvento=sitioYFecha.split(",",1)[0]  
                        fechaEvento=sitioYFecha.split(",",1)[1]
                        
                        
                        for tablaCarrera in soup4.select("table.width100.marginbot10.fonts12"):
                            for tr in tablaCarrera.select("tr")[1:]:
                                #Aquí deberiamos comprobar que existen entradas en la tabla.
                                tds=tr.select("td")
                            
                                try:
                                    pos=tds[0].text 
                                    puntos=tds[1].text
                                    numero=tds[2].text
                                    piloto=tds[3].text
                                    pais=tds[4].text
                                    equipo=tds[5].text  
                                    moto=tds[6].text  
                                    kmh=tds[7].text 
                                    tiempoDiferencia=tds[8].text 
                                    collection.insert_one({"temporada":ano,"categoria":categoria,"abreviatura":abreviaturaCarrera,"titulo":tituloCarrera,"lugar":sitioEvento,"fecha":fechaEvento, 'pos':pos,'puntos':puntos,'num':numero,'piloto':piloto,'pais':pais,'equipo':equipo,'moto':moto,'kmh':kmh,'diferencia':tiempoDiferencia})
                                    
                                    
                                except:
                                    pass
                       
                        diccionarioDocumentacion={"temporada":ano,"categoria":categoria,"abreviatura":abreviaturaCarrera,"titulo":tituloCarrera,"lugar":sitioEvento,"fecha":fechaEvento,"documentacion":arrayPDF}
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
        
                pos=tds[0].text 
                piloto=tds[1].text
                moto=tds[2].text  
                pais=tds[3].text
                puntos=tds[4].text

                collection2.insert_one({"temporada":ano,"categoria":categoriasUnicas[cont],'pos':pos,'piloto':piloto,'moto':moto,'pais':pais,'puntos':puntos})

            cont=cont+1
       




