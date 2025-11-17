package br.edu.ifsul.derick.tads_springboot.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.annotation.Secured;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserRepository repository;
    private final PerfilRepository perfilRepository; // (NOVO)
    private final BCryptPasswordEncoder passwordEncoder; // (NOVO)

    // O construtor agora recebe os novos helpers
    public UserController(UserRepository repository, PerfilRepository perfilRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> findAll(){
        return ResponseEntity.ok(repository.findAll().stream().map(UserDtoResponse::new).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        var optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    // MÉTODO INSERT ATUALIZADO
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody @Valid UserDtoPost userDtoPost, UriComponentsBuilder uriBuilder){
        // 1. Buscar o Perfil (ex: "ROLE_USER") no banco
        Perfil perfil = perfilRepository.findByNome(userDtoPost.role());
        if (perfil == null) {
            // Se não achar, é um erro do cliente
            return ResponseEntity.badRequest().build();
        }

        // 2. Criar o novo usuário
        User user = new User();
        user.setName(userDtoPost.name());
        user.setEmail(userDtoPost.email());

        // 3. CRIPTOGRAFAR A SENHA (ESSENCIAL PARA SEGURANÇA)
        user.setSenha(passwordEncoder.encode(userDtoPost.senha()));

        user.setPerfis(List.of(perfil)); // Associar o perfil ao usuário

        repository.save(user);

        var location = uriBuilder.path("api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // MÉTODO UPDATE ATUALIZADO
    @PutMapping("{id}")
    public ResponseEntity<UserDtoResponse> update(@PathVariable("id") Long id, @Valid @RequestBody UserDtoPut userDtoPut){
        var optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            var user = optionalUser.get();

            // 1. Buscar o novo Perfil
            Perfil perfil = perfilRepository.findByNome(userDtoPut.role());
            if (perfil == null) {
                return ResponseEntity.badRequest().build();
            }

            // 2. Atualizar os dados
            user.setName(userDtoPut.name());
            user.setEmail(userDtoPut.email());
            user.setPerfis(List.of(perfil)); // Atualizar o perfil
            // (Note: Não atualizamos a senha aqui. Isso é feito em outro endpoint)

            repository.save(user);

            return ResponseEntity.ok(new UserDtoResponse(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}