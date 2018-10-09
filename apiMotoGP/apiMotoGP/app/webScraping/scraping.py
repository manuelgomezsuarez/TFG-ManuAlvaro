import requests
from bs4 import BeautifulSoup
ano = 1949
docAno=requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)).text
soup = BeautifulSoup(docAno,'html5lib')
for event in soup.select("select#event > option"):
    tituloCarrera=event.get("title")
    abreviaturaCarrera=event.get("value");
    print(tituloCarrera)
    print(abreviaturaCarrera)
    
    docAnoAbreviatura = requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)).text; 
    soup2=BeautifulSoup(docAnoAbreviatura,'html5lib')
    for category in soup2.select("select#category > option"):
        categoria=category.get("value")
        print(categoria)

        docAnoAbreviaturaCategoria = requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)+"/"+str(categoria)).text;  
        soup3=BeautifulSoup(docAnoAbreviaturaCategoria,'html5lib')
        for session in soup3.select("select#session > option"):
            abreviaturaSesion=session.get("value")
            tituloSesion=session.get("title")
            print(abreviaturaSesion)
            print(tituloSesion)

            docAnoAbreviaturaCategoriaSesion = requests.get("http://www.motogp.com/es/Results+Statistics/"+str(ano)+"/"+str(abreviaturaCarrera)+"/"+str(categoria)+"/"+str(abreviaturaSesion)).text;  
            soup4=BeautifulSoup(docAnoAbreviaturaCategoriaSesion,'html5lib')
            for tablaCarrera in soup4.select("table.width100.marginbot10.fonts12"):
                cont=0;
                for tr in tablaCarrera.select("tr")[1:]:
                    #Aquí deberiamos comprobar que existen entradas en la tabla.
                    tds=tr.select("td")
                    if abreviaturaSesion=="RAC":
                        pos=tds[0].text
                        puntos=tds[1].text
                        numero=tds[2].text
                        piloto=tds[3].text
                        pais=tds[4].text
                        equipo=tds[5].text
                        moto=tds[6].text
                        kmh=tds[7].text
                        tiempoDiferencia=tds[8].text
                    else:
                        pos=tds[0].text
                        puntos=""
                        numero=tds[1].text
                        piloto=tds[2].text
                        pais=tds[3].text
                        equipo=tds[4].text
                        moto=tds[5].text
                        kmh=tds[6].text
                        tiempoDiferencia=tds[7].text

                    print(pos)
                    print(puntos)
                    print(numero)
                    print(piloto)
                    print(pais)
                    print(equipo)
                    print(moto)
                    print(kmh)
                    print(tiempoDiferencia)
                        
                    #abreviaturaSesion=session.get("value")
                    #tituloSesion=session.get("title")
                    #print(abreviaturaSesion)
                    #print(tituloSesion)

            break
        break

    break