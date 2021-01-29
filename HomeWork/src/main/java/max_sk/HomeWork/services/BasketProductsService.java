package max_sk.HomeWork.services;

import max_sk.HomeWork.dto.ProductDTO;
import max_sk.HomeWork.model.Order;
import max_sk.HomeWork.repository.OrderRepository;
import max_sk.HomeWork.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasketProductsService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private List<ProductDTO> basketList;
    private static Long orderNumber;

    @Autowired
    public BasketProductsService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void init(){
        basketList = new ArrayList<>();
        orderNumber = 1L;
    }

    public void addProdut(ProductDTO productDTO){
        for (int i = 0; i < basketList.size(); i++){
            if(basketList.get(i).getId() == productDTO.getId()){
                productDTO.incrementCount(basketList.get(i).getCount());
                productDTO.toEnlageCost(basketList.get(i).getSumCost());
                basketList.set(i, productDTO);
                return;
            }
        }
        basketList.add(productDTO);
    }

    public List<ProductDTO> getProductList(){
        return basketList;
    }

    public List<ProductDTO> incrementCount(Long id){
        ProductDTO productDTO = new ProductDTO(productRepository.getOne(id));
        for (int i = 0; i < basketList.size(); i++) {
            if (basketList.get(i).getId() == productDTO.getId()) {
                productDTO.incrementCount(basketList.get(i).getCount());
                productDTO.toEnlageCost(basketList.get(i).getSumCost());
                basketList.set(i, productDTO);
                return basketList;
            }
        }
        return basketList;
    }

    public List<ProductDTO> decrimentCount(Long id){
        ProductDTO productDTO = new ProductDTO(productRepository.getOne(id));
        for (int i = 0; i < basketList.size(); i++) {
            if (basketList.get(i).getId() == productDTO.getId()) {
                if(basketList.get(i).getCount() <= 0 & basketList.get(i).getSumCost() <= 0 ){
                    return basketList;
                }
                productDTO.decrimentCount(basketList.get(i).getCount());
                productDTO.reduceCost(basketList.get(i).getSumCost());
                basketList.set(i, productDTO);
                return basketList;
            }
        }
        return basketList;
    }
    public List<ProductDTO> deleteProductFromBasket(Long id){
        ProductDTO productDTO = new ProductDTO(productRepository.getOne(id));
        for (int i = 0; i < basketList.size(); i++) {
            if (basketList.get(i).getId() == productDTO.getId()) {
                 basketList.remove(i);
                return basketList;
            }
        }
        return basketList;
    }

    public void createOrder(){
        for (ProductDTO productDTO : basketList){
            orderRepository.saveAndFlush(new Order(
                    productDTO.getId(),
                    productDTO.getId(),
                    orderNumber,
                    productDTO.getCount(),
                    productDTO.getSumCost(),
                    new Date()
                    ));
        }
        orderNumber++;
    }
}
