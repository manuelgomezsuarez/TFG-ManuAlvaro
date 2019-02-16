from rest_framework_mongoengine import serializers
from app.models import Carreras, Campeonatos, Documentacion,distintos
 

    
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

class DistintosSerializer(serializers.DocumentSerializer):
    class Meta:
        model = distintos
        fields = '__all__'