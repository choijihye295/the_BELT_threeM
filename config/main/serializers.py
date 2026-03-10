from rest_framework import serializers
from .models import JBNU

class JBNUSerializer(serializers.ModelSerializer):
    class Meta:
        model = JBNU
        fields = ['jid', 'jtitle', 'jbelt', 'jurl','jdate']