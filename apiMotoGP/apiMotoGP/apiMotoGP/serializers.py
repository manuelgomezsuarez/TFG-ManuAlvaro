from rest_framework_mongoengine import serializers
from app.models import Carreras, Campeonatos, Documentacion
 

    
class PosicionCarreraSerializer(serializers.DocumentSerializer):
    class Meta:
        model = Carreras
        exclude=['id',]

class PosicionCampeonatoSerializer(serializers.DocumentSerializer):
    class Meta:
        model = Campeonatos
        exclude=['id',]
class PosicionDocumentacionSerializer(serializers.DocumentSerializer):
    class Meta:
        model = Documentacion
        exclude=['id',]