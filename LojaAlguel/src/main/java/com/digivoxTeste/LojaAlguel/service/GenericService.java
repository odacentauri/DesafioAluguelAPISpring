package com.digivoxTeste.LojaAlguel.service;

import com.digivoxTeste.LojaAlguel.exception.CRUDException;
import com.digivoxTeste.LojaAlguel.exception.ValidacaoException;
import com.digivoxTeste.LojaAlguel.model.AbstractEntity;
import com.digivoxTeste.LojaAlguel.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericService<T extends AbstractEntity> {

    @Autowired
    private AbstractRepository<T> repository;

    @Transactional
    public T insert(T entidade) throws CRUDException, ValidacaoException {
        return repository.save(entidade);
    }

    @Transactional
    public T update(T entidade) throws CRUDException, ValidacaoException {
        return repository.saveAndFlush(entidade);
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void remove(T entidade) throws CRUDException{
        if (exists(entidade)) {
            repository.delete(entidade);
        }
    }

    @Transactional
    public void removeById(Long id) throws CRUDException{
        if (id != null) {
            repository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public List<T> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<T> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean exists(T entidade) {
        if ((entidade.getId() == null)) {
            return false;
        }

        return repository.existsById(entidade.getId());
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
