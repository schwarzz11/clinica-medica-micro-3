package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.services.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convenios")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    @PostMapping
    public ResponseEntity<ConvenioDto> save(@RequestBody ConvenioRequest convenioRequest) {
        ConvenioDto novoConvenio = convenioService.save(convenioRequest);
        return new ResponseEntity<>(novoConvenio, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConvenioDto>> listAll() {
        return ResponseEntity.ok(convenioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConvenioDto> findById(@PathVariable Long id) {
        ConvenioDto convenioDto = convenioService.findById(id);
        if (convenioDto != null) {
            return ResponseEntity.ok(convenioDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConvenioDto> update(@PathVariable Long id, @RequestBody ConvenioRequest convenioRequest) {
        ConvenioDto updatedConvenio = convenioService.update(id, convenioRequest);
        if (updatedConvenio != null) {
            return ResponseEntity.ok(updatedConvenio);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        convenioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
