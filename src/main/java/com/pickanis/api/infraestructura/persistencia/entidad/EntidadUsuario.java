package com.pickanis.api.infraestructura.persistencia.entidad;

import com.pickanis.api.dominio.modelo.TipoDocumento;
import com.pickanis.api.dominio.modelo.Usuario;
import com.pickanis.api.infraestructura.persistencia.convertidor.ConvertidorContactoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(name = "uk_usuarios_identificacion", columnNames = {"identificacion", "nombreUsuario"})})
public class EntidadUsuario implements Serializable {
    @Id
    @NotBlank(message = "El campo Identificación no puede estar vacío")
    @Column(length = 15, unique = true, nullable = false)
    private String identificacion;

    @NotNull
    @Column(nullable = false)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo Nombre no puede estar vacío")
    @Column(length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "El campo Apellido no puede estar vacío")
    @Column(length = 50, nullable = false)
    private String apellido;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contacto")
    private EntidadContactoUsuario contactoUsuario;

    @NotBlank(message = "El campo correo no puede estar vacío")
    @Column(length = 50, nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "El campo Usuario no puede estar vacío")
    @Column(length = 30, nullable = false, unique = true)
    private String nombreUsuario;

    @NotBlank(message = "El campo Contraseña no puede estar vacío")
    @Column(length = 60, nullable = false)
    private String contrasena;

    @Column(length = 100) //Dirreccion a la ubicacionde la imagen
    private String foto;

    private Boolean habilitado;

    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "rol_id"})})
    private List<Roles> roles;

    public void actualizarInformacionPersonal(Usuario usuario) {
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.foto = usuario.getFoto();
        this.contactoUsuario = ConvertidorContactoUsuario.convertirAEntidad(usuario.getContacto());
    }

    public void desactivarCuenta() {
        this.habilitado = false;
    }
}
