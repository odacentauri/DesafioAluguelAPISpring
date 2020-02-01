package com.digivoxTeste.LojaAlguel.service;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.GlobalException;
import com.digivoxTeste.LojaAlguel.exception.ClienteException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.Cliente;
import com.digivoxTeste.LojaAlguel.model.Cliente;
import com.digivoxTeste.LojaAlguel.repository.ClienteRepository;
import com.digivoxTeste.LojaAlguel.util.ErrorConstants;
import com.digivoxTeste.LojaAlguel.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService extends GenericService<Cliente> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente insert(Cliente cliente) throws CRUDException, ValidacaoException {

        Optional<ClienteException> optItemException = validarCliente(cliente);

        if (optItemException.isPresent()){
            LOGGER.error("{}", optItemException.get());

            throw new ClienteException(optItemException.get());
        }

        try {
            Cliente clienteDB = clienteRepository.save(cliente);

            clienteDB = clienteRepository.save(clienteDB);

            return clienteDB;
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception, Cliente.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error(globalException.toString());

            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional
    public Cliente update(Cliente cliente) throws CRUDException, ValidacaoException {

        Optional<ClienteException> optionalItemException = validarCliente(cliente);

        if (optionalItemException.isPresent()){
            LOGGER.error("{}", optionalItemException.get());

            throw new ClienteException(optionalItemException.get());
        }

        try {
            return clienteRepository.save(cliente);
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception,
                    ClienteException.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error("{}", globalException);
            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        LOGGER.debug("id: {}", id);

        Optional<Cliente> optionalItem = clienteRepository.findById(id);

        if (optionalItem.isPresent()) {
            LOGGER.debug("item: {}", optionalItem.get());
        }

        return optionalItem;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(Cliente cliente) throws CRUDException {

        if (super.exists(cliente)) {
            LOGGER.debug("item: {}", cliente);

            clienteRepository.delete(cliente);
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) throws CRUDException {

        if (super.exists(id)) {
            LOGGER.debug("idItem: {}", id);

            Optional<Cliente> optionalItem = findById(id);

            if (optionalItem.isPresent()){
                Cliente cliente = optionalItem.get();

                LOGGER.debug("item: {}", cliente);

                clienteRepository.deleteById(id);
            }
        }
    }

    public Optional<ClienteException> validarCliente(Cliente cliente) {

        ClienteException itemException = new ClienteException();

        itemException.setEntidade(Cliente.class.getSimpleName());
        itemException.setServico(getClass().getSimpleName());
        itemException.setMetodo(Thread.currentThread().getStackTrace()[1].getMethodName());

        LOGGER.debug("Item: {}", cliente);

        Optional<ClienteException> optException = this.validarClienteInformado(cliente);

        if (optException.isPresent()) {
            itemException.addAlvos(optException.get().getAlvos());
            itemException.addDescricoes(optException.get().getDescricoes());
        }

        if (super.exists(cliente)){
            itemException.addAlvo(ErrorConstants.CLIENTE_JA_CADASTRADO);
            itemException.addDescricao(ErrorConstants.CLIENTE_JA_CADASTRADO_DESCRICAO);
        }

        return Optional.empty();
    }

    private Optional<ClienteException> validarClienteInformado(Cliente cliente){

        ClienteException itemException = new ClienteException();

        if (Objects.isNull(cliente)) {
            itemException.addAlvo(ErrorConstants.CLIENTE_NAO_INFORMADO);
            itemException.addDescricao(ErrorConstants.CLIENTE_NAO_INFORMADO_DESCRICAO);

            return Optional.of(itemException);
        }

        if (!itemException.getAlvos().isEmpty()){
            return Optional.of(itemException);
        }

        return Optional.empty();
    }
}
