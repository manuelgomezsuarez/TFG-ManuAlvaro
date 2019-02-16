# Python code to illustrate 
# inserting data in MongoDB 
from pymongo import MongoClient 
from pymongo import ASCENDING  
from pymongo import DESCENDING
from pymongo import collation
import unittest


try: 
    conn = MongoClient() 
    print("Connected successfully!!!") 
except:   
    print("Could not connect to MongoDB")

# database 
db = conn.motoGP_db 
collectionCarreras = db.carreras
ano=2014
categoria="MotoGP"

class TestStringMethods(unittest.TestCase):
    
    def test_primero(self):
        primero=db.campeonatos.find({"temporada":ano,"categoria":categoria}, {"puntos":1, "piloto":1,'_id':0}).sort("puntos",DESCENDING).collation({'locale':'es','numericOrdering':True})[0]
        primer_piloto=(primero["piloto"])
        primer_puntos=int(primero["puntos"])

        cursorCarrerasPrimero=collectionCarreras.find({"temporada":ano,"piloto":primer_piloto}, {"puntos":1, '_id':0})
        TotalPuntosPrimero=0;
        for n in cursorCarrerasPrimero:
            if n["puntos"] != '':
                TotalPuntosPrimero=TotalPuntosPrimero+int((n["puntos"]))


        print("primer_puntos: "+str(primer_puntos))
        print("TotalPuntosPrimero: "+str(TotalPuntosPrimero))
        self.assertEqual(primer_puntos, TotalPuntosPrimero)

    def test_quinto(self):
        quinto= db.campeonatos.find({"temporada":ano,"categoria":categoria}, {"puntos":1, "piloto":1,'_id':0}).sort("puntos",DESCENDING).collation({'locale':'es','numericOrdering':True})[5]

        quinto_piloto=(quinto["piloto"])
        quinto_puntos=int(quinto["puntos"])

        cursorCarrerasQuinto=collectionCarreras.find({"temporada":ano,"piloto":quinto_piloto}, {"puntos":1, '_id':0})
        TotalPuntosQuinto=0;
        for n in cursorCarrerasQuinto:
            if n["puntos"] != '':
                TotalPuntosQuinto=TotalPuntosQuinto+int((n["puntos"]))
        self.assertEqual(quinto_puntos, TotalPuntosQuinto)

if __name__ == '__main__':
    unittest.main()

