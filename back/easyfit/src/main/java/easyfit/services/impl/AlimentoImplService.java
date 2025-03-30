package easyfit.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easyfit.models.dtos.AlimentoDto;
import easyfit.models.dtos.AlimentoEnComidaDto;
import easyfit.models.entities.Alimento;
import easyfit.models.entities.Categoria;
import easyfit.models.entities.Usuario;
import easyfit.repositories.IAlimentoRepository;
import easyfit.repositories.ICategoriaRepository;
import easyfit.repositories.IUsuarioRepository;
import easyfit.services.IAlimentoService;

@Service
public class AlimentoImplService extends GenericCrudServiceImpl<Alimento, Integer>  implements IAlimentoService{

	@Autowired
	private IAlimentoRepository alimentoRepository;
	
	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	// En este metodo indicamos el repositorio que usamos en el CRUD genérico que hemos extendido 
	@Override
	protected IAlimentoRepository getRepository() {
		return alimentoRepository;
	}

	@Override
	public void eliminarAlimento(int idAlimento) {
		
		if (!alimentoRepository.existsById(idAlimento)) {
            throw new NoSuchElementException("Alimento no encontrado con ID: " + idAlimento);
        }
		
        alimentoRepository.deleteById(idAlimento);
    }
	
	 @Override
	    public Alimento crearAlimento(Alimento alimento) {
	        // Validaciones básicas
	        if(alimento.getNombre() == null || alimento.getNombre().isBlank()) {
	            throw new IllegalArgumentException("El nombre del alimento es obligatorio");
	        }
	        
	        if(alimento.getKcal() <= 0) {
	            throw new IllegalArgumentException("Las calorías deben ser mayores a 0");
	        }
	        
	        // Verificar y cargar relaciones
	        Usuario creador = usuarioRepository.findById(alimento.getCreadoPor().getEmail())
	                .orElseThrow(() -> new NoSuchElementException("Usuario creador no encontrado"));
	        
	        Categoria categoria = categoriaRepository.findById(alimento.getCategoria().getIdCategoria())
	                .orElseThrow(() -> new NoSuchElementException("Categoría no encontrada"));
	        
	        alimento.setCreadoPor(creador);
	        alimento.setCategoria(categoria);
	        
	        return alimentoRepository.save(alimento);
	 }

	@Override
	public Alimento modificarAlimento(int idAlimento, Alimento alimentoActualizado) {
		Alimento existente = alimentoRepository.findById(idAlimento)
		        .orElseThrow(() -> new NoSuchElementException("Alimento no encontrado con ID: " + idAlimento));

		    // Validaciones básicas
		    if(alimentoActualizado.getNombre() == null || alimentoActualizado.getNombre().isBlank()) {
		        throw new IllegalArgumentException("El nombre del alimento es obligatorio");
		    }
		    
		    if(alimentoActualizado.getKcal() <= 0) {
		        throw new IllegalArgumentException("Las calorías deben ser mayores a 0");
		    }

		    // Actualizar campos básicos
		    existente.setNombre(alimentoActualizado.getNombre());
		    existente.setMarca(alimentoActualizado.getMarca());
		    existente.setKcal(alimentoActualizado.getKcal());
		    existente.setProteinas(alimentoActualizado.getProteinas());
		    existente.setCarbohidratos(alimentoActualizado.getCarbohidratos());
		    existente.setGrasas(alimentoActualizado.getGrasas());
		    existente.setUnidadMedida(alimentoActualizado.getUnidadMedida());

		    // Actualizar relaciones si se proporcionan
		    if(alimentoActualizado.getCategoria() != null) {
		        Categoria categoria = categoriaRepository.findById(alimentoActualizado.getCategoria().getIdCategoria())
		            .orElseThrow(() -> new NoSuchElementException("Categoría no encontrada"));
		        existente.setCategoria(categoria);
		    }

		    // El creadoPor no se debe modificar, pero validamos que exista
		    if(alimentoActualizado.getCreadoPor() != null) {
		        usuarioRepository.findById(alimentoActualizado.getCreadoPor().getEmail())
		            .orElseThrow(() -> new NoSuchElementException("Usuario creador no encontrado"));
		    }

		    return alimentoRepository.save(existente);
		}

	@Override
	public List<AlimentoDto> buscarPorNombre(String nombre) {
        List<Alimento> alimentos;

        if (nombre == null || nombre.isBlank()) {
            alimentos = alimentoRepository.findAll();
        } else {
            alimentos = alimentoRepository.findByNombreContainingIgnoreCase(nombre);
        }

        return alimentos.stream()
                .map(alimento -> {
                    AlimentoDto dto = convertirADto(alimento);
                    return dto;
                })
                .collect(Collectors.toList());
    }
	
	@Override
	public List<AlimentoDto> buscarPorUsuario(String email) {
		List<Alimento> alimentos = alimentoRepository.findByCreadoPor_Email(email);

        return alimentos.stream()
                .map(alimento -> {
                    AlimentoDto dto = convertirADto(alimento);
                    return dto;
                })
                .collect(Collectors.toList());
	}
	
	
	private AlimentoDto convertirADto(Alimento alimento) {
        return new AlimentoDto(
            alimento.getIdAlimento(), alimento.getNombre(), alimento.getMarca(),
            alimento.getUnidadMedida(),
            alimento.getKcal(), alimento.getProteinas(),
            alimento.getCarbohidratos(), alimento.getGrasas()
        );
    }

	
		
	
	 
}
	


