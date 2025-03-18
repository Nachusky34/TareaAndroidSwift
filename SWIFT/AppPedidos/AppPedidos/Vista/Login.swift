import SwiftUI

struct Login: View {
    let usuarios: [Usuario] = CargarDatosUsuario()
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var mostrarAlerta: Bool = false
    @State private var isAuthenticated: Bool = false
    @State private var usuarioAutenticado: Usuario? = nil
    
    var body: some View {
        NavigationStack {
            if isAuthenticated, let usuario = usuarioAutenticado {
                Tab(usuario: Binding(
                    get: { usuario },
                    set: { usuarioAutenticado = $0 }
                ))
                .navigationBarBackButtonHidden(true)
            } else {
                VStack {
                    Image(.imgDeco2)
                        .resizable()
                        .frame(width: 225, height: 225)
                        .padding(.trailing, 204)
                        .padding(.top, -79)
                    
                    Text("PC\nera")
                        .font(.custom("Times New Roman", size: 55))
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                        .padding(.leading, 140)
                        .padding(.top, -140)
                    
                    Image(.logoCircular)
                        .resizable()
                        .frame(width: 100, height: 100)
                        .padding(.top, -40)
                    
                    
                    VStack {
                        Text("SIGN IN")
                            .font(.custom("Times New Roman", size: 32))
                            .fontWeight(.bold)
                        
                        VStack(alignment: .leading) {
                            TextField("Username", text: $username)
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                                .font(.custom("Times New Roman", size: 25))
                                .padding(.bottom, 20)
                                .padding(.top, 20)
                                .autocapitalization(.none)
                            
                            SecureField("Password", text: $password)
                                .font(.custom("Times New Roman", size: 25))
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                                .autocapitalization(.none)
                            
                            HStack {
                                Spacer()
                                
                                Text("Not have account yet?")
                                    .font(.custom("Times New Roman", size: 15))
                                
                                NavigationLink(destination: Registro().navigationBarBackButtonHidden(true)) {
                                        Text("SIGN UP")
                                            .font(.custom("Times New Roman", size: 15))
                                            .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                                    }
                                
                                Spacer()
                            }
                            .padding(.top, 10)
                            
                                
                        }
                    }
                    .padding(.horizontal, 45)
                    .padding(.bottom, 90)

                    Button(action: {
                        print(usuarios)
                        if let usuarioValido = usuarios.first(where: { $0.username == username && $0.password == password }) {
                            usuarioAutenticado = usuarioValido
                            isAuthenticated = true
                            mostrarAlerta = false
                            
                        } else {
                            mostrarAlerta = true
                        }
                    }) {
                        Text("SIGN IN")
                            .font(.custom("Times New Roman", size: 18))
                            .frame(width: 90, height: 20)
                            .padding()
                            .background(Color.blue.opacity(0.2))
                            .foregroundColor(.blue)
                            .cornerRadius(10)
                    }
                    .background(Color.clear)
                    .contentShape(Rectangle())
                    .padding(.horizontal, 100)
                    .padding(.trailing, 160)
                    .padding(.bottom, -60)
                    
                    .alert(isPresented: $mostrarAlerta) {
                        Alert(
                            title: Text("Error"),
                            message: Text("Username or password incorrect"),
                            dismissButton: .default(Text("OK"))
                        )
                    }
                    
                    Image(.imgDeco1)
                        .resizable()
                        .frame(width: 225, height: 225)
                        .padding(.leading, 204)
                        .padding(.bottom, -51)
                }
                .padding()
            }
        }
    }
}


#Preview {
    Login()
}
