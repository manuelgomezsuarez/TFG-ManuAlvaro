from django.shortcuts import render
from rest_framework_mongoengine import viewsets as meviewsets
from apiMotoGP.serializers import PosicionCarreraSerializer, PosicionCampeonatoSerializer,PosicionDocumentacionSerializer    
from app.models import  Carreras, Campeonatos,Documentacion
import django_filters.rest_framework
from django_filters.rest_framework import DjangoFilterBackend
from django.http import HttpResponse
from django.template import loader
from rest_framework import filters
from rest_framework.utils.urls import remove_query_param, replace_query_param
from rest_framework.pagination import PageNumberPagination



def index(request):
    template = loader.get_template('app/index.html')
    context = {
      
    }
    return HttpResponse(template.render(context,request))

class PosicionCarreraViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCarreraSerializer
    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        my_filter_fields = ('piloto', 'num','temporada','categoria','abreviatura','titulo','lugar','fecha','pos','puntos','pais','equipo','moto','kmh')
        for field in  self.request.query_params: # iterate over the filter fields
            if field.split("__")[0] in my_filter_fields:
                field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
                if field_value: 
                    filtering_kwargs[field] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Carreras.objects.all().order_by('fecha')
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        distinctUrl= self.request.query_params.get('distinct', None)
        if distinctUrl is not None:
            queryset = Carreras.objects.filter(**filtering_kwargs).order_by('fecha').distinct(distinctUrl) # filter the queryset based on
            arrayQuerySet=[]
            DictDistintos={}
            for q in queryset:
                DictDistintos=({distinctUrl:q})
                arrayQuerySet.append(DictDistintos)
            print(queryset)
            return arrayQuerySet
        else:
            if filtering_kwargs:
                queryset = Carreras.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
                print(queryset)
            return queryset
    http_method_names = ['get']




class PosicionCampeonatoViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCampeonatoSerializer

    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        my_filter_fields = ('temporada', 'categoria','pos','piloto','moto','pais','puntos')
        for field in  self.request.query_params: # iterate over the filter fields
            if field.split("__")[0] in my_filter_fields:
                field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
                if field_value: 
                    filtering_kwargs[field] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Campeonatos.objects.all()
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        distinctUrl= self.request.query_params.get('distinct', None)
        if distinctUrl is not None:
            queryset = Campeonatos.objects.filter(**filtering_kwargs).distinct(distinctUrl) # filter the queryset based on
            queryset.sort()
            arrayQuerySet=[]
            DictDistintos={}
            for q in queryset:
                DictDistintos=({distinctUrl:q})
                arrayQuerySet.append(DictDistintos)
            return arrayQuerySet
        else:
            if filtering_kwargs:
                queryset = Campeonatos.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
            return queryset
    http_method_names = ['get']


class PosicionDocumentacionViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionDocumentacionSerializer

    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        my_filter_fields = ('temporada', 'categoria','abreviatura','titulo','lugar','fecha')
        for field in  self.request.query_params: # iterate over the filter fields
            if field.split("__")[0] in my_filter_fields:
                field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
                if field_value: 
                    filtering_kwargs[field] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Documentacion.objects.all() 
        print(queryset)
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        distinctUrl= self.request.query_params.get('distinct', None)
        if distinctUrl is not None:
            queryset = Documentacion.objects.filter(**filtering_kwargs).distinct(distinctUrl) # filter the queryset based on
            queryset.sort()
            arrayQuerySet=[]
            DictDistintos={}
            for q in queryset:
                DictDistintos=({distinctUrl:q})
                arrayQuerySet.append(DictDistintos)
            print(queryset)
            return arrayQuerySet
        else:
            if filtering_kwargs:
                queryset = Documentacion.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
            return queryset
    http_method_names = ['get']