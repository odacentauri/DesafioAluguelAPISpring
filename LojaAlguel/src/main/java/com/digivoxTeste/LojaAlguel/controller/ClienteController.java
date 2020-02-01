package com.digivoxTeste.LojaAlguel.controller;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.Cliente;
import com.digivoxTeste.LojaAlguel.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(description = "Controlador responsável pelos serviços de Cliente")
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value = "Insere um novo cliente na base de dados.", response = Cliente.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Object> insert(
            @ApiParam(value = "Objeto Cliente com as informações que serão cadastradas na base de dados. ", required = true)
            @RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>(clienteService.insert(cliente), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Atualiza as informações de um cliente na base de dados.", response = Cliente.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(
            @ApiParam(value = "Objeto Cliente com as informações que serão atualizadas na base de dados. ", required = true)
            @RequestBody Cliente cliente) {
        try {
            return new ResponseEntity<>(clienteService.update(cliente), HttpStatus.OK);
        } catch (ValidacaoException | CRUDException e) {
            return new ResponseEntity<>(e, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @ApiOperation(value = "Recupera um cliente na base de dados, através do ID.", response = Cliente.class)
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(
            @ApiParam(value = "ID do cliente cadastrado na base de dados. ", required = true)
            @RequestParam("id") Long id) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        if (optionalCliente.isPresent()){
            return new ResponseEntity<>(optionalCliente.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Lista os itens cadastrados na base de dados.", response = Cliente.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<Cliente> optionalCliente = clienteService.getAll();
        if (!optionalCliente.isEmpty()){
            return new ResponseEntity(optionalCliente, HttpStatus.OK);
        }
        return new ResponseEntity(Boolean.FALSE, HttpStatus.PRECONDITION_FAILED);
    }

    @ApiOperation(value = "Remove um cliente cadastrado na base de dados.", response = Boolean.class)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Object> remove(
            @ApiParam(value = "Objeto cliente cadastrado na base de dados.", required = true)
            @RequestBody Cliente cliente) throws CRUDException {
        clienteService.remove(cliente);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove um cliente cadastrado na base de dados, através do ID.", response = Boolean.class)
    @RequestMapping(value = "/removeById", method = RequestMethod.POST)
    public ResponseEntity<Object> removeById(
            @ApiParam(value = "ID do cliente a ser excluido na base de dados.", required = true)
            @RequestParam("id") Long id) throws CRUDException {
        clienteService.removeById(id);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
