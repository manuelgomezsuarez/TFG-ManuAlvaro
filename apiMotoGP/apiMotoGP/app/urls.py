from django.conf.urls import url
from rest_framework_mongoengine import routers as merouters
from app.views import PosicionCarreraViewSet,PosicionCampeonatoViewSet,PosicionDocumentacionViewSet
 
merouter = merouters.DefaultRouter()
merouter.register('carrera', PosicionCarreraViewSet,base_name="carrera")
merouter.register('campeonato', PosicionCampeonatoViewSet,base_name="campeonato")
merouter.register('documentacion', PosicionDocumentacionViewSet,base_name="documentacion")


urlpatterns = [
 
]
 
urlpatterns += merouter.urls