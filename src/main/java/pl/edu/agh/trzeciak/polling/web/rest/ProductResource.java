package pl.edu.agh.trzeciak.polling.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.agh.trzeciak.polling.domain.Product;
import pl.edu.agh.trzeciak.polling.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/app")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private ProductRepository productRepository;

    /**
     * POST  /rest/products -> Create a new product.
     */
    @RequestMapping(value = "/rest/products",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Product product) {
        log.debug("REST request to save Product : {}", product);
        productRepository.save(product);
    }

    /**
     * GET  /rest/products -> get all the products.
     */
    @RequestMapping(value = "/rest/products",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> getAll() {
        log.debug("REST request to get all Products");
        return productRepository.findAll();
    }

    /**
     * GET  /rest/products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productRepository.delete(id);
    }
}
