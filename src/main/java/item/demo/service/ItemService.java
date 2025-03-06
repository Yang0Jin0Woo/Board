package item.demo.service;

import item.demo.domain.Item;
import item.demo.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Item findItem(Long id){
        return itemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Item> findListItem(){
        return itemRepository.findAll();
    }

    public void updateItem(Item updateItem, Long id){
        Item item = itemRepository.findById(id);
        if(item != null){
            item.setName(updateItem.getName());
            item.setPrice(updateItem.getPrice());
            item.setStockQuantity(updateItem.getStockQuantity());
        }
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id);
        if(item != null) {
            itemRepository.delete(item);
        }
    }
}
