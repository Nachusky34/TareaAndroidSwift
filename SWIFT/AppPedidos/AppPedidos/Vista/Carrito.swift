import SwiftUI

struct Carrito: View {
    @State private var ordenadores: [Ordenador] = CargarDatosOrdenador()
    @State private var compras: [Compra] = CargarDatosCompra()
    @Environment(\.presentationMode) var presentacion
    let usuario: Usuario
    
    var comprasFiltradas: [Compra] {
        compras.filter { !$0.comprado }
    }
    
    var total: Double {
        comprasFiltradas.reduce(0.0) { acumulador, compra in
            if let ordenador = ordenadores.first(where: { $0.id == compra.idProducto }) {
                return acumulador + (Double(compra.cantidad) * ordenador.precio)
            }
            return acumulador
        }
    }
    
    var body: some View {
        VStack {
            HStack {
                Button(action: {
                    presentacion.wrappedValue.dismiss()
                }) {
                    Image(systemName: "arrow.left.circle.fill")
                        .font(.system(size: 40))
                        .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                }
                Spacer()
                Text("Carrito")
                    .font(.custom("Times New Roman", size: 40))
                    .fontWeight(.bold)
                    .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                
            }
            .padding(.horizontal, 30)
            .padding(.top, 20)
            
            List(comprasFiltradas) { compra in
                if let ordenador = ordenadores.first(where: { $0.id == compra.idProducto }) {
                    HStack(spacing: 10) {
                        Image(ordenador.img)
                            .resizable()
                            .frame(width: 100, height: 100)
                            .cornerRadius(8)
                        
                        VStack(alignment: .leading) {
                            Text(ordenador.nombre)
                                .font(.headline)
                                .bold()
                            
                            Text("Cantidad: \(compra.cantidad)")
                                .font(.subheadline)
                            
                            Text(String(format: "%.2f$", ordenador.precio * Double(compra.cantidad)))
                                .font(.title2)
                                .foregroundColor(.blue)
                        }
                        Spacer()
                        
                        Button(action: {
                            eliminarCompra(compra)
                        }) {
                            Image(systemName: "trash")
                                .foregroundColor(.white)
                                .frame(width: 40, height: 40)
                                .background(Color.red)
                                .clipShape(RoundedRectangle(cornerRadius: 8))
                        }
                    }
                    .padding()
                    .background(RoundedRectangle(cornerRadius: 10).stroke(Color.gray, lineWidth: 1))
                }
            }
            
            HStack() {
                Text("Total: \(String(format: "%.2f$", total))")
                    .font(.custom("Times New Roman", size: 25))
                    .padding()
                    .padding(.top, 20)
                    .frame(maxWidth: .infinity, alignment: .center)
                    .frame(maxHeight: .infinity)
                    .background(Color.white)
                                
                Button(action: {
                    print("Compra realizada")
                }) {
                    Text("COMPRAR YA")
                        .font(.custom("Times New Roman", size: 25))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity)
                        .frame(maxHeight: .infinity)
                        .padding()
                        .padding(.top, 20)
                        .background(Color(red: 85/255, green: 183/255, blue: 232/255))
                        
                }
            }
            .frame(height: 100)
            .padding(0)
            
        }
    }
    
    func eliminarCompra(_ compra: Compra) {
        compras.removeAll { $0.id == compra.id }
        agregarCompra(idUsuario: compra.idUsuario, idProducto: compra.idProducto, cantidad: compra.cantidad)
    }
}

#Preview {
    let usuarioFicticio = Usuario(id: 12, username: "marioseoane", password: "12", email: "marioseoane@marioseoane.marioseoane", postalcode: "12345", newsletter: true, foto: "marioseoane")
    Carrito(usuario: usuarioFicticio)
}
