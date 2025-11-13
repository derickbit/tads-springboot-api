package br.edu.ifsul.derick.tads_springboot.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/users") // Alinhado ao padrão do professor
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    // GET Todos (retorna DTO de Resposta)
    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> findAll(){
        // Usa o padrão do professor: .stream().map(CONSTRUTOR::new).toList()
        return ResponseEntity.ok(repository.findAll().stream().map(UserDtoResponse::new).toList());
    }

    // GET por ID (retorna a Entidade, como no exemplo do professor)
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        var optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST (Recebe DTO Post, retorna URI)
    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody @Valid UserDtoPost userDtoPost, UriComponentsBuilder uriBuilder){
        // Usa o construtor que criamos na entidade User
        var user = repository.save(new User(userDtoPost));

        // Constrói a URI de resposta, como o professor fez
        var location = uriBuilder.path("api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // PUT (Recebe DTO Put, retorna DTO Resposta)
    @PutMapping("{id}")
    public ResponseEntity<UserDtoResponse> update(@PathVariable("id") Long id, @Valid @RequestBody UserDtoPut userDtoPut){
        // Busca o usuário existente primeiro (Melhoria em relação ao exemplo)
        var optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            var user = optionalUser.get();

            // Atualiza os dados do usuário existente
            user.setName(userDtoPut.name());
            user.setEmail(userDtoPut.email());
            user.setRole(userDtoPut.role());
            // (Note: Não atualizamos o HASH da senha aqui)

            repository.save(user); // Salva as alterações

            return ResponseEntity.ok(new UserDtoResponse(user));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE (Lógica idêntica à do professor)
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}