# ListDogRetrofit
Qué es un API REST?
API REST como un servicio que nos provee de las funciones que necesitamos para poder obtener información de un cliente externo, como por ejemplo, una base de datos alojada en cualquier parte del mundo desde dentro de nuestra propia aplicación

Las librerías que vamos a importar son las siguientes:

-Picasso: Esta librería nos permitirá transformar esas urls en imágenes.
-Retrofit 2: Librería encargada del consumo de las API.
-Retrofit 2 Converter Gson: Esta herramienta será un complemento a la anterior y nos simplificará el proceso de pasar un JSON a una Data Class que es con lo que trabajaremos en nuestro proyecto.
-Corrutinas: Entre otras muchas cosas nos permitirá hacer las peticiones de Retrofit en segundo plano para no bloquear la interfaz del usuario (lo explicaré más a fondo durante el artículo).
-Escondiendo el teclado
 Una vez haya buscado no va a esconderse el teclado y visualmente queda bastante feo. Para ello simplemente añade esta función.

  private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
    VIDEO
    https://www.youtube.com/watch?v=aQP-mUGWh1U
