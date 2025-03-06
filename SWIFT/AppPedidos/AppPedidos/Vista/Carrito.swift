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
                        VStack {
                            
                            Image(ordenador.img)
                                .resizable()
                                .frame(width: 100, height: 100)
                                .cornerRadius(8)
                            
                            Text(compra.fecha)
                                .font(.custom("Times New Roman", size: 14))
                            
                            Text(compra.hora)
                                .font(.custom("Times New Roman", size: 14))
                        }
                        VStack(alignment: .leading) {
                            Text(ordenador.nombre)
                                .font(.custom("Times New Roman", size: 20))
                                .bold()
                            
                            Text("Cantidad: \(compra.cantidad)")
                                .font(.custom("Times New Roman", size: 15))
                                .padding(.top, 15)
                            
                            
                            Text(String(format: "%.2f$", ordenador.precio * Double(compra.cantidad)))
                                .font(.title2)
                                .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                                .padding(.top, 20)
                                .padding(.leading, 10)
                            
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
                    .background(RoundedRectangle(cornerRadius: 10).stroke(Color.gray, lineWidth: 1).frame(width: 320, height: 180))
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
        eliminarCompraJson(compra: compra)
        
        //Actualizar los datos de compra
        compras = CargarDatosCompra()
    }
}

#Preview {
    let usuarioFicticio = Usuario(id: 12, username: "marioseoane", password: "12", email: "marioseoane@marioseoane.marioseoane", postalcode: "12345", newsletter: true, foto: "marioseoane")
    Carrito(usuario: usuarioFicticio)
}
