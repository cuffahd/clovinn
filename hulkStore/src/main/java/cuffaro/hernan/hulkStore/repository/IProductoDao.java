package cuffaro.hernan.hulkStore.repository;

import org.springframework.data.repository.CrudRepository;

import cuffaro.hernan.hulkStore.model.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {}
