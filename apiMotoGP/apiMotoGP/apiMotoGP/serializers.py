from rest_framework_mongoengine import serializers
from app.models import Carreras, Campeonatos, Documentacion
 

    
class PosicionCarreraSerializer(serializers.DocumentSerializer):
    class Meta:
        model = Carreras
        fields = '__all__'

class PosicionCampeonatoSerializer(serializers.DocumentSerializer):
    class Meta:
        model = Campeonatos
        fields = '__all__'

class PosicionDocumentacionSerializer(serializers.DocumentSerializer):
    class Meta:
        model = Documentacion
        fields = '__all__'