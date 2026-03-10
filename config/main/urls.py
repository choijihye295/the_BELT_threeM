from django.urls import path, include
from .views import HelloAPI, JBNUsApiMixins, JBNUApiMixins

urlpatterns = [
    path("hello/", HelloAPI),
    path("mixin/jbnus", JBNUsApiMixins.as_view()),
    path("mixin/jbnu/<int:jid>/", JBNUApiMixins.as_view()),
]

from rest_framework import routers
from .views import JBNUViewSet

router = routers.SimpleRouter()
router.register('JBNUs', JBNUViewSet)

urlpatterns = router.urls