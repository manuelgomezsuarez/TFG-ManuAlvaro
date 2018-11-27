from django.shortcuts import render
 
from rest_framework_mongoengine import viewsets as meviewsets
from apiMotoGP.serializers import PosicionCarreraSerializer, PosicionCampeonatoSerializer, PosicionDocumentacionSerializer
from app.models import  Carreras, Campeonatos, Documentacion

class PosicionCarreraViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCarreraSerializer

    def get_queryset(self):
        queryset=Carreras.objects.all()
        temporadaUrl= self.request.query_params.get('temporada', None)
        if temporadaUrl is not None:
            queryset = queryset.filter(temporada=temporadaUrl)

        categoriaUrl = self.request.query_params.get('categoria', None)
        if categoriaUrl is not None:
            queryset = queryset.filter(categoria=categoriaUrl)

        abreviaturaUrl = self.request.query_params.get('abreviatura', None)
        if abreviaturaUrl is not None:
            queryset = queryset.filter(abreviatura=abreviaturaUrl)

        tituloUrl = self.request.query_params.get('titulo', None)
        if tituloUrl is not None:
            queryset = queryset.filter(titulo=tituloUrl)

        lugarUrl = self.request.query_params.get('lugar', None)
        if lugarUrl is not None:
            queryset = queryset.filter(lugar=lugarUrl)

        fechaUrl = self.request.query_params.get('fecha', None)
        if lugarUrl is not None:
            queryset = queryset.filter(fecha=fechaUrl)

        posUrl = self.request.query_params.get('pos', None)
        if posUrl is not None:
            queryset = queryset.filter(pos=posUrl)

        puntosUrl = self.request.query_params.get('puntos', None)
        if puntosUrl is not None:
            queryset = queryset.filter(puntos=puntosUrl)

        numUrl = self.request.query_params.get('num', None)
        if numUrl is not None:
            queryset = queryset.filter(num=numUrl)

        pilotoUrl = self.request.query_params.get('piloto', None)
        if pilotoUrl is not None:
            queryset = queryset.filter(piloto=pilotoUrl)

        paisUrl = self.request.query_params.get('pais', None)
        if paisUrl is not None:
            queryset = queryset.filter(pais=paisUrl)

        equipoUrl = self.request.query_params.get('equipo', None)
        if equipoUrl is not None:
            queryset = queryset.filter(equipo=equipoUrl)

        motoUrl = self.request.query_params.get('moto', None)
        if motoUrl is not None:
            queryset = queryset.filter(moto=motoUrl)

        kmhUrl = self.request.query_params.get('kmh', None)
        if kmhUrl is not None:
            queryset = queryset.filter(kmh=kmhUrl)

        diferenciaUrl = self.request.query_params.get('diferencia', None)
        if diferenciaUrl is not None:
            queryset = queryset.filter(diferencia=diferenciaUrl)

        return queryset
    
class PosicionCampeonatoViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCampeonatoSerializer

    def get_queryset(self):
        queryset=Campeonatos.objects.all()
        temporadaUrl= self.request.query_params.get('temporada', None)
        if temporadaUrl is not None:
            queryset = queryset.filter(temporada=temporadaUrl)

        categoriaUrl = self.request.query_params.get('categoria', None)
        if categoriaUrl is not None:
            queryset = queryset.filter(categoria=categoriaUrl)

        posUrl = self.request.query_params.get('pos', None)
        if posUrl is not None:
            queryset = queryset.filter(pos=posUrl)

        pilotoUrl = self.request.query_params.get('piloto', None)
        if pilotoUrl is not None:
            queryset = queryset.filter(piloto=pilotoUrl)

        motoUrl = self.request.query_params.get('moto', None)
        if motoUrl is not None:
            queryset = queryset.filter(moto=motoUrl)

        paisUrl = self.request.query_params.get('pais', None)
        if paisUrl is not None:
            queryset = queryset.filter(pais=paisUrl)

        puntosUrl = self.request.query_params.get('puntos', None)
        if puntosUrl is not None:
            queryset = queryset.filter(puntos=puntosUrl)

        return queryset

class PosicionDocumentacionViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionDocumentacionSerializer

    def get_queryset(self):
        queryset=Documentacion.objects.all()
        temporadaUrl= self.request.query_params.get('temporada', None)
        if temporadaUrl is not None:
            queryset = queryset.filter(temporada=temporadaUrl)

        categoriaUrl = self.request.query_params.get('categoria', None)
        if categoriaUrl is not None:
            queryset = queryset.filter(categoria=categoriaUrl)

        abreviaturaUrl = self.request.query_params.get('abreviatura', None)
        if abreviaturaUrl is not None:
            queryset = queryset.filter(abreviatura=abreviaturaUrl)

        tituloUrl = self.request.query_params.get('titulo', None)
        if tituloUrl is not None:
            queryset = queryset.filter(titulo=tituloUrl)

        lugarUrl = self.request.query_params.get('lugar', None)
        if lugarUrl is not None:
            queryset = queryset.filter(lugar=lugarUrl)

        fechaUrl = self.request.query_params.get('fecha', None)
        if lugarUrl is not None:
            queryset = queryset.filter(fecha=fechaUrl)

        return queryset
    