package com.digivoxTeste.LojaAlguel.service;

import com.digivoxTeste.LojaAlguel.exception.*;
import com.digivoxTeste.LojaAlguel.model.Aluguel;
import com.digivoxTeste.LojaAlguel.model.Item;
import com.digivoxTeste.LojaAlguel.repository.AluguelRepository;
import com.digivoxTeste.LojaAlguel.repository.ItemRepository;
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
public class AluguelService extends GenericService<Aluguel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AluguelService.class);

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Override
    @Transactional
    public Aluguel insert(Aluguel aluguel) throws CRUDException, ValidacaoException {

        Optional<AluguelException> optAluguelException = validarAluguel(aluguel);

        if (optAluguelException.isPresent()){
            LOGGER.error("{}", optAluguelException.get());

            throw new AluguelException(optAluguelException.get());
        }

        try {
            Aluguel aluguelDB = aluguelRepository.save(aluguel);

            aluguelDB = aluguelRepository.save(aluguelDB);
            return aluguelDB;
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception, Aluguel.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error(globalException.toString());

            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional
    public Aluguel update(Aluguel aluguel) throws CRUDException, ValidacaoException {

        Optional<AluguelException> optionalAluguelException = validarAluguel(aluguel);

        if (optionalAluguelException.isPresent()){
            LOGGER.error("{}", optionalAluguelException.get());

            throw new AluguelException(optionalAluguelException.get());
        }

        try {
            return aluguelRepository.save(aluguel);
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception,
                    Aluguel.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error("{}", globalException);
            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Aluguel> findById(Long id) {
        LOGGER.debug("id: {}", id);

        Optional<Aluguel> optionalAluguel = aluguelRepository.findById(id);

        optionalAluguel.ifPresent(aluguel -> LOGGER.debug("aluguel: {}", aluguel));

        return optionalAluguel;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aluguel> getAll() {
        return aluguelRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(Aluguel aluguel) throws CRUDException {

        if (super.exists(aluguel)) {
            LOGGER.debug("aluguel: {}", aluguel);

            aluguelRepository.delete(aluguel);
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) throws CRUDException {

        if (super.exists(id)) {
            LOGGER.debug("idAluguel: {}", id);

            Optional<Aluguel> optionalAluguel = findById(id);

            if (optionalAluguel.isPresent()){
                Aluguel aluguel = optionalAluguel.get();

                LOGGER.debug("aluguel: {}", aluguel);

                aluguelRepository.deleteById(id);
            }
        }
    }

    public Optional<AluguelException> validarAluguel(Aluguel aluguel) {

        AluguelException aluguelException = new AluguelException();

        aluguelException.setEntidade(Aluguel.class.getSimpleName());
        aluguelException.setServico(getClass().getSimpleName());
        aluguelException.setMetodo(Thread.currentThread().getStackTrace()[1].getMethodName());

        LOGGER.debug("Aluguel: {}", aluguel);

        Optional<AluguelException> optException = this.validarAluguelInformado(aluguel);

        if (optException.isPresent()) {
            aluguelException.addAlvos(optException.get().getAlvos());
            aluguelException.addDescricoes(optException.get().getDescricoes());
        }

        if (super.exists(aluguel)){
            aluguelException.addAlvo(ErrorConstants.ALUGUEL_JA_CADASTRADO);
            aluguelException.addDescricao(ErrorConstants.ALUGUEL_JA_CADASTRADO_DESCRICAO);
        }

        if (!aluguelException.getAlvos().isEmpty()){
            return Optional.of(aluguelException);
        }

        return Optional.empty();
    }

    private Optional<AluguelException> validarAluguelInformado(Aluguel aluguel){

        AluguelException aluguelException = new AluguelException();

        if (Objects.isNull(aluguel)) {
            aluguelException.addAlvo(ErrorConstants.ALUGUEL_NAO_INFORMADO);
            aluguelException.addDescricao(ErrorConstants.ALUGUEL_NAO_INFORMADO_DESCRICAO);

            return Optional.of(aluguelException);
        }

        if (!itemRepository.findById(aluguel.getItemAlugado().getId()).isPresent()){
            aluguelException.addAlvo(ErrorConstants.ITEM_NAO_EXISTE);
            aluguelException.addDescricao(ErrorConstants.ITEM_NAO_EXISTE_DESCRICAO);

            return Optional.of(aluguelException);
        }

        if (!aluguelException.getAlvos().isEmpty()){
            return Optional.of(aluguelException);
        }

        return Optional.empty();
    }
}
