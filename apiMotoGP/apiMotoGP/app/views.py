from django.shortcuts import render
from rest_framework_mongoengine import viewsets as meviewsets
from apiMotoGP.serializers import PosicionCarreraSerializer, PosicionCampeonatoSerializer, PosicionDocumentacionSerializer
from app.models import  Carreras, Campeonatos, Documentacion
import django_filters.rest_framework
from app.filters import CampeonatoFilter
from django_filters.rest_framework import DjangoFilterBackend

class PosicionCarreraViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCarreraSerializer
    my_filter_fields = ('piloto', 'num','temporada','categoria','abreviatura','titulo','lugar','fecha','pos','puntos','pais','equipo','moto') # specify the fields on which you want to filter
    #filter_backends = (filters.OrderingFilter,)
    #ordering_fields = ('username', 'email')
    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        for field in  self.my_filter_fields: # iterate over the filter fields
            field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
            if field_value: 
                filtering_kwargs[field+ '__icontains'] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Carreras.objects.all() 
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        if filtering_kwargs:
            queryset = Carreras.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
        return queryset




class PosicionCampeonatoViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCampeonatoSerializer
    filter_backends = (django_filters.rest_framework.DjangoFilterBackend,)
    filter_fields=('piloto',)
    def get_queryset(self):
        queryset=Campeonatos.objects.all()
        return [i for i in queryset]

    
    #filter_backends = (filters.OrderingFilter,)
    #ordering_fields = ('username', 'email')
    #def get_queryset(self):
    #    queryset = Carreras.objects.all() 
    #    filter_backends = (django_filters.rest_framework.DjangoFilterBackend,)
    #    return queryset




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
    