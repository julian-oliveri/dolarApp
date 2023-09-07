# dolarApp
Una simple App de Kotlin para chequear el valor del dolar y otras divisas

La aplicacion consiste de 2 Fragmentos:

HomeFragment 
que esta compuesto por

-Tablayout: que es lo que maneja si en la pantalla se muestran el valor del dolar o el valor de otras monedas (aca se usaron tabs y no botones por un tema estetico, es el mismo fragmento el que se muestra)<br />
-ReciclerView: un recycler que es el encargado de mostrar la data que se recibe del endpoint con ayuda de CurrencyValueAdapter y CurrencyValueViewHolder<br />
-Boton “Guardar datos” es el encargado de meter los datos del dolar oficial y del  dolar blue en la base de datos<br />
-Boton “Linea de tiempo” que es el que manda al siguiente fragmento<br />

TimelineFragment
que esta compuesto por
-Tablayout: que es lo que maneja si en la pantalla se muestran el valor de compra o de venta en el grafico (aca se usaron tabs y no botones por un tema estetico, es el mismo fragmento el que se muestra)<br />
-LineChart es un grafico de lineas que te muestra el cambio de los valores segun la fecha requerida (esto lo implemente de https://github.com/PhilJay/MPAndroidChart)<br />
-ReciclerView: un recycler que es el encargado de mostrar la data que se recibe del endpoint y de la base de datos con ayuda de ValueSearchAdapter y ValueSearchViewHolder<br />
-Boton “Buscar” que es el que abre un CustomDialog<br />

El CustomDialog se compone por 1 Spinner al que le agrego los valores mediante un StringArray y dos DatePicker que se encargan de obtener la fecha de inicio y de final de la busqueda.
Cuando el usuario termina de elegir los valores se hace una llamada a un metodo que decide si los datos se van a sacar de la base de datos (si el usuario eligio MI HISTORICO) o si se van a pedir al back, el id de cada peticion se saca de un valor dentro de otro stringArray que corresponde a la respuesta del endpoint de https://www.dolarsi.com/adm/api/estadisticas/?type=getMonedas 


La arquitectura que se utilizo es mvvm, con la ayuda de Retrofit para hacer las llamadas
Se hacen las siguientes llamadas dentro de la app

getDolarValue hacia  https://www.dolarsi.com/api/api.php?type=valoresprincipales que es la que  recibe el valor actual del dolar

getCurrencyValue hacie  https://www.dolarsi.com/api/api.php?type=cotizador que es la que recibe el valor actual de las otras monedas

getSearchData hacia  https://www.dolarsi.com/adm/api/estadisticas/?type=getHistoricoAndPrincipales que es la llamada que recibe los resultados de la busqueda pasandole un id de la moneda, una fecha de inicio para la busqueda y una fecha de finalizacion


Para la base de datos use SQLite, donde cree una tabla en la cual inserto

precio compra dolar oficial
precio venta dolar oficial
precio compra dolar blue
precio venta dolar blue
fecha donde se guardo
 
