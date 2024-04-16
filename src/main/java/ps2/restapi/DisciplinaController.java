package ps2.restapi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DisciplinaController {

    private List<Disciplina> disciplinas;

    public DisciplinaController() {
        this.disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina(1, "Programação", "PROG", "Engenharia da Computação", 1));
        disciplinas.add(new Disciplina(2, "Biologia", "BIO", "Ciências Biológicas", 2));
    }

    @GetMapping("/api/disciplinas")
    Iterable<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    @GetMapping("/api/disciplinas/{id}")
    Optional<Disciplina> getDisciplina(@PathVariable long id) {
        return disciplinas.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }

    @PostMapping("/api/disciplinas")
    Disciplina createDisciplina(@RequestBody Disciplina disciplina) {
        long maxId = disciplinas.stream()
                .mapToLong(Disciplina::getId)
                .max()
                .orElse(0);
        disciplina.setId(maxId + 1);
        disciplinas.add(disciplina);
        return disciplina;
    }

    @PutMapping("/api/disciplinas/{id}")
    Optional<Disciplina> updateDisciplina(@RequestBody Disciplina disciplinaRequest, @PathVariable long id) {
        Optional<Disciplina> opt = this.getDisciplina(id);
        if (opt.isPresent()) {
            Disciplina disciplina = opt.get();
            disciplina.setNome(disciplinaRequest.getNome());
            disciplina.setSigla(disciplinaRequest.getSigla());
            disciplina.setCurso(disciplinaRequest.getCurso());
            disciplina.setSemestre(disciplinaRequest.getSemestre());
        }
        return opt;
    }

    @DeleteMapping("/api/disciplinas/{id}")
    void deleteDisciplina(@PathVariable long id) {
        disciplinas.removeIf(d -> d.getId() == id);
    }
}
