#main/views.py
from rest_framework import viewsets, permissions, generics, status
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.decorators import api_view

from rest_framework.generics import get_object_or_404
from .models import JBNU #모델
from .serializers import JBNUSerializer #시리얼라이저

@api_view(['GET']) #데코레이터
def HelloAPI(request):
    return Response("hello world!")

from rest_framework import generics
from rest_framework import mixins
class JBNUsApiMixins(mixins.ListModelMixin, mixins.CreateModelMixin, generics.GenericAPIView):
    queryset = JBNU.objects.all()
    serializer_class = JBNUSerializer

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)
    
class JBNUApiMixins(mixins.RetrieveModelMixin, mixins.UpdateModelMixin, mixins.DestroyModelMixin, generics.GenericAPIView):
    queryset = JBNU.objects.all()
    serializer_class = JBNUSerializer
    lookup_field = 'jid'

    def get(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)
    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)

from rest_framework import viewsets
class JBNUViewSet(viewsets.ModelViewSet):
    queryset=JBNU.objects.all()
    serializer_class = JBNUSerializer