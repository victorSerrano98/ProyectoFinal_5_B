from django.contrib import admin

# Register your models here

from .models import Cliente
from .models import Banco
from .models import Cuenta
from .models import Transaccion

class AdminCliente(admin.ModelAdmin):
    list_display = ["cedula", "nombres", "apellidos", "genero", "estadoCivil", "fechaNacimiento", "correo", "telefono", "celular", "direccion"]
    list_editable = ["apellidos", "nombres", "genero"]
    list_filter = ["genero", "direccion", "fechaNacimiento", "estadoCivil"]
    search_fields = ["cedula", "nombres", "apellidos"]

    class Meta:
        model = Cliente

admin.site.register(Cliente, AdminCliente)


class AdminBanco(admin.ModelAdmin):
    list_display = ["nombre", "direccion", "telefono", "correo"]
    list_editable = ["direccion", "telefono", "correo"]
    list_filter = ["direccion"]
    search_fields = ["nombre", "correo"]

    class Meta:
        model = Banco

admin.site.register(Banco, AdminBanco)


class AdminCuenta(admin.ModelAdmin):
    list_display = ["numero", "estado", "fechaApertura", "saldo", "tipoCuenta", "cliente"]
    list_filter = ["fechaApertura", "estado", "tipoCuenta"]
    search_fields = ["numero", "cliente"]

    class Meta:
        model = Cuenta

admin.site.register(Cuenta, AdminCuenta)


class AdminTransaccion(admin.ModelAdmin):
    list_display = ["fecha", "tipo", "valor", "descripcion", "responsable", "cuenta"]
    list_filter = ["tipo", "responsable"]
    search_fields = ["cuenta", "descripcion"]

    class Meta:
        model = Transaccion

admin.site.register(Transaccion, AdminTransaccion)


