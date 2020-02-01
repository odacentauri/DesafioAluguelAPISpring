package com.digivoxTeste.LojaAlguel.service;


import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.GlobalException;
import com.digivoxTeste.LojaAlguel.exception.TipoItemException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.TipoItem;
import com.digivoxTeste.LojaAlguel.repository.TipoItemRepository;
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
public class TipoItemService extends GenericService<TipoItem> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TipoItemService.class);

    @Autowired
    private TipoItemRepository tipoItemRepository;

    @Override
    @Transactional
    public TipoItem insert(TipoItem tipoItem) throws CRUDException, ValidacaoException {

        Optional<TipoItemException> optItemException = validarTipoItem(tipoItem);

        if (optItemException.isPresent()){
            LOGGER.error("{}", optItemException.get());

            throw new TipoItemException(optItemException.get());
        }

        try {
            TipoItem tipoItemDB = tipoItemRepository.save(tipoItem);

            tipoItemDB = tipoItemRepository.save(tipoItemDB);

            return tipoItemDB;
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception, TipoItem.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error(globalException.toString());

            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional
    public TipoItem update(TipoItem tipoItem) throws CRUDException, ValidacaoException {

        Optional<TipoItemException> optionalItemException = validarTipoItem(tipoItem);

        if (optionalItemException.isPresent()){
            LOGGER.error("{}", optionalItemException.get());

            throw new TipoItemException(optionalItemException.get());
        }

        try {
            return tipoItemRepository.save(tipoItem);
        } catch (ConstraintViolationException exception) {
            GlobalException globalException = ExceptionUtils.montarConstraintViolationException(exception,
                    TipoItemException.class.getSimpleName(), getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            LOGGER.error("{}", globalException);
            throw new ValidacaoException(globalException);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoItem> findById(Long id) {
        LOGGER.debug("id: {}", id);

        Optional<TipoItem> optionalItem = tipoItemRepository.findById(id);

        if (optionalItem.isPresent()) {
            LOGGER.debug("item: {}", optionalItem.get());
        }

        return optionalItem;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoItem> getAll() {
        return tipoItemRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(TipoItem tipoItem) throws CRUDException {

        if (super.exists(tipoItem)) {
            LOGGER.debug("item: {}", tipoItem);

            tipoItemRepository.delete(tipoItem);
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) throws CRUDException {

        if (super.exists(id)) {
            LOGGER.debug("idItem: {}", id);

            Optional<TipoItem> optionalItem = findById(id);

            if (optionalItem.isPresent()){
                TipoItem tipoItem = optionalItem.get();

                LOGGER.debug("item: {}", tipoItem);

                tipoItemRepository.deleteById(id);
            }
        }
    }

    public Optional<TipoItemException> validarTipoItem(TipoItem tipoItem) {

        TipoItemException itemException = new TipoItemException();

        itemException.setEntidade(TipoItem.class.getSimpleName());
        itemException.setServico(getClass().getSimpleName());
        itemException.setMetodo(Thread.currentThread().getStackTrace()[1].getMethodName());

        LOGGER.debug("Item: {}", tipoItem);

        Optional<TipoItemException> optException = this.validarTipoItemInformado(tipoItem);

        if (optException.isPresent()) {
            itemException.addAlvos(optException.get().getAlvos());
            itemException.addDescricoes(optException.get().getDescricoes());
        }

        if (super.exists(tipoItem)){
            itemException.addAlvo(ErrorConstants.TIPO_ITEM_JA_CADASTRADO);
            itemException.addDescricao(ErrorConstants.TIPO_ITEM_JA_CADASTRADO_DESCRICAO);
        }

        return Optional.empty();
    }

    private Optional<TipoItemException> validarTipoItemInformado(TipoItem tipoItem){

        TipoItemException itemException = new TipoItemException();

        if (Objects.isNull(tipoItem)) {
            itemException.addAlvo(ErrorConstants.TIPO_ITEM_NAO_INFORMADO);
            itemException.addDescricao(ErrorConstants.TIPO_ITEM_NAO_INFORMADO_DESCRICAO);

            return Optional.of(itemException);
        }

        if (!itemException.getAlvos().isEmpty()){
            return Optional.of(itemException);
        }

        return Optional.empty();
    }
}
