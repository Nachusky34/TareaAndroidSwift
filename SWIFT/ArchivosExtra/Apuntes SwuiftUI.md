## Índice:

[**FUNCIONES	2**](#funciones)

[**CLASES	2**](#clases)

[**Botón:	3**](#botón:)

[**Botón Gradiente:	4**](#botón-gradiente:)

[**Botón Pulsar y Mantener	5**](#botón-pulsar-y-mantener)

[**Imágenes:	6**](#imágenes:)

[**Fuentes:	7**](#fuentes:)

[**Tipos textos:	8**](#tipos-textos:)

[**Formas:	9**](#formas:)

[**Login:	11**](#login:)

[**Editor Texto:	12**](#editor-texto:)

[**DatePickerFormat:	13**](#datepickerformat:)

[**ColorPicker:	13**](#colorpicker:)

[**ProgressView con barra de progreso y botones Start, Stop e Inicializar	14**](#progressview-con-barra-de-progreso-y-botones-start,-stop-e-inicializar)

[**LINK	15**](#link)

[**Toggle WIFI	16**](#toggle-wifi)

[**STEPPER CONTADOR	16**](#stepper-contador)

[**Slider con valor dinámico y texto que cambia de color al editar	17**](#slider-con-valor-dinámico-y-texto-que-cambia-de-color-al-editar)

[**Slider con máximo dinámico y mensajes personalizados	17**](#slider-con-máximo-dinámico-y-mensajes-personalizados)

[**ScrollView horizontal	18**](#scrollview-horizontal)

[**Listas	19**](#listas)

[**Modificación de texto:	20**](#modificación-de-texto:)

[**Interacción:	20**](#interacción:)

[Estilo de Fondos y Bordes	21](#estilo-de-fondos-y-bordes)

[Modificación de Formas	21](#modificación-de-formas)

[Interfaz de Usuario	21](#interfaz-de-usuario)

[Componentes Específicos	21](#componentes-específicos)

[**Otros	22**](#otros)

## 

# FUNCIONES {#funciones}
```
func myFunc(name: String, age: Int) \-\> String {  
    return "Mi nombre es \\(name) y mi edad es \\(age)"  
}

print(myFunc(name: "Nacho", age: 19))
```
# CLASES {#clases}

## **Clase persona con un constructor y una función para decir Hola**
```
class Person {  
    var name: String  
    var age: Int  
      
    init(name: String, age: Int) { // Constructor  
        self.name \= name // self \= this en java  
        self.age \= age  
    }  
    func saludar() {  
        print("Hola, soy \\(name)")  
    }  
}

var personaAna \= Person(name: "Ana", age: 22\)  
personaAna.saludar()
```
## **Herencia**
```
class Student: Person {  
    var asignaturas: Int  
      
    init(name: String, age: Int, asignaturas: Int) {  
        self.asignaturas \= asignaturas  
        super.init(name: name, age: age)  
    }  
      
    // override significa que ya tienes este método implementado en la clase padre,  
    // pero se va a cambiar en esta subclase  
    override func saludar() {  
        print("Hola, soy \\(name) y soy estudiante de la UEM")  
    }  
}

var estudiante \= Student(name: "Sara", age: 24, asignaturas: 3\)  
estudiante.saludar()

```
# 

# Botón: {#botón:}

```

struct ContentView: View {  
    var body: some View {  
        VStack {  
            Button("Botón básico"){  
                print("He pulsado el botón básico")  
            }  
              
            // Button(action: {}, label: {} )  
            Button(action: {  
                print("Me he suscrito")  
            }, label: {  
                Text("Suscríbete".uppercased())  
                    .font(.largeTitle)  
                    .fontWeight(.bold)  
                    .foregroundColor(.white)  
                    .padding()  
                    .background(  
                        Color.red  
                    )  
                    .cornerRadius(10)  
                    .shadow(radius: 10\)  
            })  
              
            Button(action: {  
                print("Le he dado Like")  
            }, label: {  
                Circle()  
                    .fill(.blue)  
                    .frame(width: 200, height: 200\)  
                    .shadow(radius: 10\)  
                    .overlay(  
                        Image(systemName: "hand.thumbsup.fill")  
                            .foregroundColor(.white)  
                            .font(.system(size:100, weight: .bold))  
                    )  
                  
            })  
              
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
}
```

# Botón Gradiente: {#botón-gradiente:}

```

struct ContentView: View {  
    var body: some View {  
        VStack {  
            Button(action: {  
                print("Se ha presionado el botón")  
            }, label: {  
                Text("Presióname")  
                    .font(.headline)  
                    .foregroundColor(.white)  
                    .padding()  
                    .background(LinearGradient(gradient: Gradient(colors: \[Color.purple,   
Color.blue  
\]), startPoint: .leading, endPoint: .trailing))  
                    .cornerRadius(10)  
            })  
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
} 

```

# Botón Pulsar y Mantener {#botón-pulsar-y-mantener}

```
struct ContentView: View {  
    var body: some View {  
        VStack {  
            Label("Suscríbete",  
                  systemImage: "hand.thumbsup.fill")  
            .padding()  
            .foregroundColor(.blue)  
            .onTapGesture {  
                print("Suscríbete tocado")  
            }  
            .onLongPressGesture{  
                print("Suscríbete largo")  
            }  
              
            Label("Suscríbete a swift",  
                  systemImage: "hand.thumbsup.fill")  
            .labelStyle(TitleOnlyLabelStyle())  
            .padding()  
            .foregroundColor(.red)  
              
            Label("Suscríbete a DAM",  
                  systemImage: "hand.thumbsup.fill")  
            .labelStyle(IconOnlyLabelStyle())  
            .padding()  
            .foregroundColor(.green)  
              
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
} 

```

# 

# Imágenes: {#imágenes:}
```
struct ContentView: View {  
    var body: some View {  
        VStack {  
            Image("youtube")  
                .resizable()  
                .scaledToFit()  
                .frame(width:80, height:80)  
                .padding()  
                .onTapGesture {  
                    print("Youtube tocado")  
                }  
              
            Image("youtube")  
                .resizable()  
                .renderingMode(.template)  
                .foregroundColor(.green)  
                .scaledToFit()  
                .frame(width:80)  
                .padding()  
                .onTapGesture {  
                    print("Youtube tocado")  
                }  
              
            Image(systemName: "square.and.arrow.up.circle.fill")  
                .resizable()  
                .scaledToFit()  
                .frame(width:80, height:80)  
                .padding()  
                .onTapGesture {  
                    print("Youtube tocado")  
                }  
              
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
} 
```
# Fuentes: {#fuentes:}
```
struct ContentView: View {  
    var body: some View {  
        VStack {  
            // Predefinidos \- Funcionan todas pero hay que escribirlas a poquitos  
            Text("Font largeTitle")  
                .font(.largeTitle)  
            Divider()  
            Text("Font title")  
                .font(.title)  
            Divider()  
            Text("Font title2")  
                .font(.title2)  
            Divider()  
            Text("Font title3")  
                .font(.title3)  
            Divider()  
            Text("Font headline")  
                .font(.headline)  
            Divider()  
            Text("Font subheadline")  
                .font(.subheadline)  
            Divider()  
            Text("Font body")  
                .font(.body)  
            Divider()  
            Text("Font callout")  
                .font(.callout)  
            Divider()  
            Text("Font caption")  
                .font(.caption)  
            Divider()  
            Text("Font caption2")  
                .font(.caption2)  
            Divider()  
            Text("Font footnote")  
                .font(.footnote)  
            Divider()  
              
              
            //Personalizados  
            Text("Font de 30 puntos")  
                .font(.system(size: 30))  
                .padding()  
                .italic()  
            Divider()  
            Text("Font de 17 puntos, negrita y redondeado")  
                .font(.system(size: 17, weight: .bold, design: .rounded))  
                .italic()  
            Divider()  
              
            Text("Default").font(.system(.body, design: .default, weight: .light))  
            Text("Monospaced").font(.system(.body, design: .monospaced, weight:.medium))  
            Text("Serif").font(.system(.body, design: .serif, weight: .heavy))  
            Text("Rounded").font(.system(.body, design: .rounded, weight: .bold))  
            // probar también para wweight: .ultraLight,.thin, .light,.regular,.medium,.semibold,.bold,.heavy,.black  
              
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
}
```
# Tipos textos: {#tipos-textos:}
```
Text("Font largeTitle")  
                    .font(.largeTitle)  
                Divider()  
                Text("Font title")  
                    .font(.title)  
                Divider()  
                Text("Font title2")  
                    .font(.title2)  
                Divider()  
                Text("Font title3")  
                    .font(.title3)  
                Divider()  
                Text("Font headline")  
                    .font(.headline)  
                Divider()  
                Text("Font subheadline")  
                    .font(.subheadline)  
                Divider()  
                Text("Font body")  
                    .font(.body)  
                Divider()  
                Text("Font callout")  
                    .font(.callout)  
                Divider()  
                Text("Font caption")  
                    .font(.caption)  
                Divider()  
                Text("Font caption2")  
                    .font(.caption2)  
                Divider()  
                Text("Font footnote")  
                    .font(.footnote)  
                Divider()  
                  
                  
                //Personalizados  
                Text("Font de 30 puntos")  
                    .font(.system(size: 30))  
                    .padding()  
                    .italic()  
                Divider()  
                Text("Font de 17 puntos, negrita y redondeado")  
                    .font(.system(size: 17, weight: .bold, design: .rounded))  
                    .italic()  
                Divider()  
                  
                Text("Default").font(.system(.body, design: .default, weight: .light))  
                Text("Monospaced").font(.system(.body, design: .monospaced, weight:.medium))  
                Text("Serif").font(.system(.body, design: .serif, weight: .heavy))  
                Text("Rounded").font(.system(.body, design: .rounded, weight: .bold))  
                // probar también para wweight: .ultraLight,.thin, .light,.regular,.medium,.semibold,.bold,.heavy,.black 
```
# Formas: {#formas:}
```
struct ContentView: View {  
    var body: some View {  
        VStack {  
            Circle()  
                .fill(  
Color.red  
)  
                .frame(width:100, height: 100\)  
                .padding()  
            Divider()  
            ZStack{  
                Rectangle()  
                    .frame(width:120, height: 80\)  
                    .foregroundColor(.green)  
                    .border(  
Color.black  
, width: 3\)  
                Text("Feliz Navidad\!")  
                    .foregroundColor(.red)  
                    .bold()  
            }  
              
            RoundedRectangle(cornerRadius: 20\)  
                .fill(.yellow)  
                .frame(width:120, height: 80\)  
              
            Ellipse()  
                .fill(.blue)  
                .frame(width:120, height: 80\)  
              
            Capsule()  
                .fill(.gray)  
                .frame(width:120, height: 60\)

            Ellipse()  
                .stroke(.pink, lineWidth: 3\)  
                .frame(width:120, height: 80\)  
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
} 
```
# Login: {#login:}
```
struct ContentView: View {  
      
    //@Binding var usr: String  
    @State var usr: String \= ""  
    @State var pwd: String \= ""

    var body: some View {  
        VStack {  
            TextField ("Username", text: $usr)  
                .keyboardType(.emailAddress)  
                .disableAutocorrection(true)  
                .autocapitalization(.none)  
                .font(.headline)  
                .background(Color.red.opacity(0.8))  
                .cornerRadius(6)  
                .padding(.horizontal, 60\)  
                .foregroundStyle(Color.black)  
                .onChange(of: usr) { oldValue, newValue in  
                    print("Username nuevo valor: \\(newValue)")  
                }  
              
            SecureField ("Password", text: $pwd)  
                .disableAutocorrection(true)  
                .autocapitalization(.none)  
                .font(.headline)  
                .background(Color.red.opacity(0.8))  
                .cornerRadius(6)  
                .padding(.horizontal, 60\)  
                .foregroundStyle(Color.black)  
                .onChange(of: pwd) { oldValue, newValue in  
                    print("Contraseña nuevo valor: \\(newValue)")  
                }  
              
            Button ("Autenticar ✅") {  
                print("\*\*\*\*\*\*\*\*\*\*")  
                print(" 👤 \\(usr)")  
                print("🔑 \\(pwd)")  
            }  
              
            Button ("Limpiar campos 🧹") {  
                usr \= ""  
                pwd \= ""  
                print("Borraste todo")  
            }  
              
            Spacer()	  
        }  
        .padding()  
    }  
}

\#Preview {  
    // Para que la variable @Binding funcione hay que inicializarla en el ContentView:  
    //ContentView(usr: .constant(""))  
    ContentView()  
}
```
# Editor Texto: {#editor-texto:}
```
struct ContentView: View {  
      
    // @State permite acceder a las variables desde la misma vista  
    // @Binding nos servirá para variables en distintas vistas  
    @State var texto: String \= ""  
    @State var contador \= 0  
      
    var body: some View {  
        VStack {  
            Text("EDITOR DE TEXTO")  
                .bold()  
              
            TextEditor(text: $texto)  
                .disableAutocorrection(true)  
                .padding(8)  
                .font(.title)  
                .padding(.horizontal, 60\)  
              
                .onChange(of: texto) { oldValue, newValue in  
                    contador \= newValue.count  
                }  
              
            HStack{  
                Button("Limpiar texto"){  
                    texto \= ""  
                }  
                  
                Spacer()  
                Text("Caracteres: \\(contador)")  
            }  
              
              
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
}
```
# DatePickerFormat: {#datepickerformat:}
```
struct ContentView: View {  
      
    @State var fecha: Date \= Date()  
      
    var body: some View {  
        VStack {  
            DatePicker("Selecciona una fecha", selection: $fecha)  
                .datePickerStyle(GraphicalDatePickerStyle())

            Text("Fecha seleccionada: \\(formatearFecha(date: fecha))")  
        }  
        .padding()  
    }  
}

func formatearFecha(date: Date) \-\> String {  
    let formatear \= DateFormatter()  
    formatear.dateStyle \= .medium  
    return formatear.string(from: date)  
}

\#Preview {  
    ContentView()  
}
```
# ColorPicker: {#colorpicker:}
```
struct ContentView: View {  
      
    @State var color: Color \= .blue  
      
    var body: some View {  
        VStack {  
            Rectangle()  
                .foregroundColor(color)  
                .frame(width: 300, height: 50\)  
            ColorPicker("Color del rectángulo: ", selection: $color)  
            Spacer()  
        }  
        .padding()  
    }  
}

\#Preview {  
    ContentView()  
}
```
# ProgressView con barra de progreso y botones Start, Stop e Inicializar {#progressview-con-barra-de-progreso-y-botones-start,-stop-e-inicializar}
```
import SwiftUI

struct ContentView: View {  
    @State var isLoading: Bool \= false  
    @State private var progress \= 0.0

    var body: some View {  
        VStack {  
            HStack {  
                Text("Min")  
                Spacer()  
                Text("Max")  
            }  
            .padding()  
              
            if isLoading {  
                ProgressView(value: progress, total: 1.0)  
                    .padding(.horizontal, 30\)  
                    .padding(.bottom, 20\)  
                  
                ProgressView()  
                    .progressViewStyle(CircularProgressViewStyle(tint: .blue))  
                    .scaleEffect(2)  
            }  
              
            Text("Apúntate a DAM").padding()  
              
            HStack(spacing: 30\) {  
                Button("Start") {  
                    isLoading \= true  
                    progress \= min(progress \+ 0.1, 1.0)  
                }  
                Button("Stop") {  
                    isLoading \= false  
                }  
                Button("Inicializar") {  
                    isLoading \= false  
                    progress \= 0.0  
                }  
            }  
        }  
    }  
}
```
# LINK {#link}
```
struct ContentView: View {  
    var body: some View {  
        VStack {  
            Link(destination: URL(string: "https://meet.jit.si/ios")\!) {  
                Text("Unirse a la llamada de Jitsi")  
                    .font(.title)  
            }

            Link("Settings", destination: URL(string: UIApplication.openSettingsURLString)\!)  
        }  
    }  
}

\#Preview {  
    ContentView()  
}
```
# Toggle WIFI  {#toggle-wifi}
```
struct ContentView: View {  
    @State var isOn: Bool \= false

    var body: some View {  
        Form {  
            Toggle("Encender wifi:", isOn: $isOn)  
            Text("El toggle está en \\(isOn.description)")  
        }  
    }  
}

\#Preview {  
    ContentView()  
}
```
# STEPPER CONTADOR {#stepper-contador}
```
struct ContentView: View {  
    @State var cuentaCosas: Int \= 0

    var body: some View {  
        Form {  
            Stepper("Cosas: \\(cuentaCosas)") {  
                cuentaCosas \+= 1  
                print("Incrementar 1: Cosas \\(cuentaCosas)")  
            } onDecrement: {  
                cuentaCosas \-= 1  
                print("Decrementar 1: Cosas \\(cuentaCosas)")  
            }  
        }  
    }  
}

\#Preview {  
    ContentView()  
}
```
# Slider con valor dinámico y texto que cambia de color al editar {#slider-con-valor-dinámico-y-texto-que-cambia-de-color-al-editar}
```
struct ContentView: View {  
    @State var contador \= 0.0  
    @State var editando: Bool \= false

    var body: some View {  
        Form {  
            Slider(value: $contador, in: 0...10, step: 0.1, onEditingChanged: { editing in  
                editando \= editing  
            }, minimumValueLabel: Text("min"), maximumValueLabel: Text("max")) {  
                Text("Contador")  
            }

            Text("\\(contador)")  
                .foregroundColor(editando ? .green : .black)  
        }  
    }  
}

\#Preview {  
    ContentView()  
}
```
# Slider con máximo dinámico y mensajes personalizados {#slider-con-máximo-dinámico-y-mensajes-personalizados}
```
struct ContentView: View {  
    @State private var valor: Double \= 0

    var body: some View {  
        Text("Exámenes de la asignatura").font(.title)  
        NavigationStack {  
            Form {  
                Slider(value: $valor, in: 1...1000, step: 1, minimumValueLabel: Text("min").font(.title3), maximumValueLabel: Text("max").font(.title3)) {  
                    Text("Valor del Slider")  
                }  
                Text("¡¡¡ Dale hasta el máximo \!\!\!...").font(.title3)  
                Text("Cantidad de exámenes a poner en la asignatura: \\(valor, specifier: "%.0f") ").font(.title3)

                if valor \> 999 {  
                    Text("Osea : Todos")  
                        .font(.title3)  
                        .foregroundColor(.red)  
                        .bold()  
                        .padding()  
                        .background(Color.yellow)  
                        .cornerRadius(20)  
                    Text("Imposible realizar más").font(.title3)  
                }  
            }  
            .padding()  
            .navigationTitle("¿Cuántos suspensos hay?")  
        }  
    }  
}

\#Preview {  
    ContentView()  
}
```
# ScrollView horizontal  {#scrollview-horizontal}
```
struct ContentView: View {  
    @State var elementos \= 1...100

    var body: some View {  
        ScrollView(.horizontal) {  
            Grid {  
                HStack {  
                    ForEach(elementos, id: \\.self) { elem in  
                        GridRow {  
                            Circle()  
                                .frame(height: 40\)  
                                .foregroundColor(.blue)  
                            Text("\\(elem)")  
                        }  
                    }  
                }  
            }  
        }  
    }  
}

\#Preview {  
    ContentView()  
}
```
# Listas {#listas}
```
struct Dispositivo {  
    let titulo: String  
    let nomImagen: String  
}

let hogar \= \[  
    Dispositivo(titulo: "Laptop", nomImagen: "laptopcomputer"),  
    Dispositivo(titulo: "Mac mini", nomImagen: "macmini"),  
    Dispositivo(titulo: "Mac Pro", nomImagen: "macpro.gen3"),  
    Dispositivo(titulo: "Pantallas", nomImagen: "display.2"),  
    Dispositivo(titulo: "Apple TV", nomImagen: "appletv")  
\]

let trabajo \= \[  
    Dispositivo(titulo: "iPhone", nomImagen: "iphone"),  
    Dispositivo(titulo: "iPad", nomImagen: "ipad"),  
    Dispositivo(titulo: "Airpods", nomImagen: "airpods"),  
    Dispositivo(titulo: "Apple Watch", nomImagen: "applewatch")  
\]

struct ContentView: View {  
    var body: some View {  
        List {  
            Section(header: Text("Hogar").foregroundColor(.red).bold()) {  
                ForEach(hogar, id: \\.titulo) { opcion in  
                    Label(opcion.titulo, systemImage: opcion.nomImagen)  
                }  
            }

            Section(header: Text("Trabajo").foregroundColor(.red).bold()) {  
                ForEach(trabajo, id: \\.titulo) { opcion in  
                    Label(opcion.titulo, systemImage: opcion.nomImagen)  
                }  
            }  
        }  
        .listStyle(SidebarListStyle())  
    }  
}

\#Preview {  
    ContentView()  
}
```
# JSONS

# Modificación de texto: {#modificación-de-texto:}

**`.font()`**  
Cambia el tamaño y estilo de la fuente. Ejemplos: `.largeTitle`, `.headline`, `.system(size: 17, weight: .bold)`.  
**`.italic()`**  
Aplica estilo de cursiva al texto.  
**`.foregroundColor()`**  
Cambia el color del texto.  
**`.bold()`**  
Aplica negrita al texto.  
**`.padding()`**  
Añade espacio alrededor del texto.  
**`.cornerRadius()`**  
Redondea las esquinas del fondo.

# Interacción: {#interacción:}

**`.onTapGesture {}`**  
Detecta un toque simple en el componente.  
**`.onLongPressGesture {}`**  
Detecta una pulsación larga en el componente.  
**`.onChange(of:variable) {}`**  
Observa cambios en una variable y ejecuta un bloque de código.

# Estilo de Fondos y Bordes {#estilo-de-fondos-y-bordes}

* **`.background()`**  
  Añade un color o gradiente como fondo detrás del componente.  
* **`.shadow(radius:)`**  
  Aplica una sombra al componente.  
* **`.border(color:, width:)`**  
  Añade un borde al componente con un color y ancho específicos.  
* **`.overlay()`**  
  Superpone un componente sobre otro (por ejemplo, un icono encima de un círculo).

# Modificación de Formas {#modificación-de-formas}

* **`.fill()`**  
  Rellena una forma con un color.  
* **`.stroke()`**  
  Dibuja solo el contorno de una forma.  
* **`.frame(width:, height:)`**  
  Define el ancho y alto de un componente.

# Interfaz de Usuario {#interfaz-de-usuario}

* **`.resizable()`**  
  Permite escalar una imagen a un tamaño diferente.  
* **`.scaledToFit()`**  
  Escala un componente manteniendo sus proporciones.  
* **`.renderingMode(.template)`**  
  Cambia el modo de renderizado de la imagen para usarla como plantilla (por ejemplo, para cambiar el color con `.foregroundColor()`).

# Componentes Específicos {#componentes-específicos}

* **`.keyboardType()`**  
  Define el tipo de teclado que aparece al editar un campo (por ejemplo, `.emailAddress`).  
* **`.autocapitalization()`**  
  Configura si el texto comienza en mayúsculas (por ejemplo, `.none`).  
* **`.disableAutocorrection()`**  
  Desactivar la autocorrección en un campo de texto.  
* **`.datePickerStyle()`**  
  Cambia el estilo visual del selector de fecha (por ejemplo, `GraphicalDatePickerStyle()`).

# Otros {#otros}

* **`.uppercased()`**  
  Convierte un texto a mayúsculas.  
* **`.frame()`**  
  Cambia el tamaño de un componente (ancho y alto).

