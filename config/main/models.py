from django.db import models

# Create your models here.
class JBNU(models.Model):
    jid = models.IntegerField(primary_key=True) #id
    jtitle = models.CharField(max_length=50) #공지사항 제목
    jbelt = models.CharField(max_length=50) #공지사항 벨트
    jurl = models.CharField(max_length=50) #공지사항 url
    jdate = models.DateField() #공지사항 날짜
