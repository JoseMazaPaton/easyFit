package easyfit.services.impl;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import easyfit.models.dtos.admin.UserResumenDto;
import easyfit.models.dtos.admin.UsuarioAdminListaDto;
import easyfit.models.dtos.admin.UsuarioPorMesDto;
import easyfit.models.dtos.auth.UsuarioResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Sexo;
import easyfit.models.enums.TipoRol;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IUsuarioAdminService;
@Service
public class UsuarioAdminServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioAdminService{

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	protected JpaRepository<Usuario, String> getRepository() {
		// TODO Auto-generated method stub
		return usuarioRepository;
	}

	@Override
	public List<Usuario> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findBySexo(Sexo sexo) {
		// TODO Auto-generated method stub
		return usuarioRepository.findBySexo(sexo);
	}

	@Override
	public List<Usuario> findByEdad(int edad) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEdad(edad);
	}

	//Método para la suspensión de la cuenta de usuario.
	@Override
	public UsuarioAdminListaDto cambiarEstadoUsuario(String email) {
	    Usuario usuario = usuarioRepository.findById(email)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    usuario.setSuspendida(!usuario.isSuspendida());
	    Usuario usuarioActualizado = usuarioRepository.save(usuario);

	    return UsuarioAdminListaDto.builder()
	        .nombre(usuarioActualizado.getNombre())
	        .email(usuarioActualizado.getEmail())
	        .sexo(usuarioActualizado.getSexo())
	        .suspendida(usuarioActualizado.isSuspendida())
	        .edad(usuarioActualizado.getEdad())
	        .altura(usuarioActualizado.getAltura())
	        .fechaRegistro(usuarioActualizado.getFechaRegistro())
	        .build();
	}
	
	//Método para obtener todos los usuarios
	@Override
	public List<UsuarioAdminListaDto> obtenerUsuarios () {
		
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		
		return listaUsuarios.stream()
				 .filter(usuario -> usuario.getIdRol() != null && 
                 usuario.getIdRol().getNombre() == TipoRol.ROL_USUARIO)
				.map(usuario -> mapper.map(usuario, UsuarioAdminListaDto.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public UserResumenDto obtenerResumenUsuarios() {
	    LocalDate hoy = LocalDate.now();
	    LocalDate primerDiaMesActual = hoy.withDayOfMonth(1);
	    LocalDate primerDiaMesAnterior = primerDiaMesActual.minusMonths(1);
	    LocalDate primerDiaHace6Meses = primerDiaMesActual.minusMonths(5);

	    List<Usuario> usuariosUltimos6Meses = usuarioRepository.findByFechaRegistroBetween(primerDiaHace6Meses, hoy);
	    List<Usuario> registradosEsteMes = usuarioRepository.findByFechaRegistroBetween(primerDiaMesActual, hoy);
	    List<Usuario> registradosMesAnterior = usuarioRepository.findByFechaRegistroBetween(primerDiaMesAnterior, primerDiaMesActual.minusDays(1));
	    List<Usuario> activos = usuarioRepository.findBySuspendidaFalse();
	    List<Usuario> hombres = usuarioRepository.findBySexo(Sexo.HOMBRE);
	    List<Usuario> mujeres = usuarioRepository.findBySexo(Sexo.MUJER);

	    Map<String, UsuarioPorMesDto> mapa = new LinkedHashMap<>();
	    for (int i = 5; i >= 0; i--) {
	        LocalDate mesInicio = primerDiaMesActual.minusMonths(i);
	        String nombreMes = mesInicio.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es"));
	        nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1); 

	        int hombresMes = (int) usuariosUltimos6Meses.stream()
	            .filter(u -> u.getSexo() == Sexo.HOMBRE && u.getFechaRegistro().getMonth() == mesInicio.getMonth())
	            .count();

	        int mujeresMes = (int) usuariosUltimos6Meses.stream()
	            .filter(u -> u.getSexo() == Sexo.MUJER && u.getFechaRegistro().getMonth() == mesInicio.getMonth())
	            .count();

	        mapa.put(nombreMes, UsuarioPorMesDto.builder()
	            .mes(nombreMes)
	            .hombres(hombresMes)
	            .mujeres(mujeresMes)
	            .build());
	    }

	    return UserResumenDto.builder()
	        .registradosEsteMes(registradosEsteMes.size())
	        .registradosMesAnterior(registradosMesAnterior.size())
	        .mediaRegistros6Meses(usuariosUltimos6Meses.size() / 6.0)
	        .totalUsuariosActivos(activos.size())
	        .totalHombres(hombres.size())
	        .totalMujeres(mujeres.size())
	        .registrosPorMes(new ArrayList<>(mapa.values()))
	        .build();
	}

}

