import SwiftUI

struct Tab: View {
    // Aquí almacenas el usuario autenticado
    @Binding var usuario: Usuario
    
    var body: some View {
        if usuario.id == -1 {
                    Login()
        } else {
            TabView {
                Perfil(usuario: $usuario)
                    .tabItem {
                        Label("Perfil", systemImage: "person")
                    }
                    .tag(1)
                
                // Otros tabs, por ejemplo, Tienda y Historial, también deberían tener la instancia del usuario si es necesario.
                Text("Tienda")
                    .tabItem {
                        Label("Tienda", systemImage: "house")
                    }
                    .tag(2)
                
                Text("Historial")
                    .tabItem {
                        Label("Historial", systemImage: "clock.arrow.trianglehead.counterclockwise.rotate.90")
                    }
                    .tag(3)
            }
            .accentColor(Color(red: 85/255, green: 183/255, blue: 232/255))
        }
    }
}

#Preview {
    let usuarioFicticio = Usuario(id: 12, username: "marioseoane", password: "12", email: "marioseoane@marioseoane.marioseoane", postalcode: "12345", newsletter: true, foto: "marioseoane")
    Tab(usuario: .constant(usuarioFicticio))
}
