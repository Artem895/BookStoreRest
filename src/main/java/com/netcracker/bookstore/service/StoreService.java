package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.model.Store;
import com.netcracker.bookstore.repo.Purchaserepository;
import com.netcracker.bookstore.repo.Storerepository;
import com.netcracker.bookstore.serializer.store.StoreCustomSerrializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class StoreService {

    private final Storerepository storerepository;
    private final Purchaserepository purchaserepository;
    private static StoreService instance;
    @Autowired
    public StoreService(Storerepository storerepository, Purchaserepository purchaserepository) {
        this.storerepository = storerepository;
        this.purchaserepository = purchaserepository;
    }
    @PostConstruct
    void init() { instance = this; }

    public static StoreService getInstance() { return instance; }

    public Store findbyid(int id){
        return storerepository.findById(id).orElse(new Store());
    }
    public List<Store> findall(){
        return storerepository.findAll();
    }
    public Store update(Store newstore) throws UpdateExeption {
        Store oldstore=storerepository.findById(newstore.getSid()).orElseThrow(()->new UpdateExeption("такого id нет "));
        if(newstore.getLogo() != null) {
            oldstore.setLogo(newstore.getLogo());
        }
        if (Double.compare(newstore.getProfit(), 0.0) !=0){
            oldstore.setProfit(newstore.getProfit());
        }
        if(newstore.getSarial()!=null){
            oldstore.setSarial(newstore.getSarial());
        }

        return storerepository.save(oldstore);
    }


    public void delete(Store store){
        List<Purchase> allpurchasewiththisstore=purchaserepository.findAllByStore(store);
        purchaserepository.deleteAll(allpurchasewiththisstore);
        storerepository.delete(store);
    }
    public Store save(Store store){
        return storerepository.save(store);
    }

    public String logobyarial(String... arials) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Store.class, new StoreCustomSerrializer());
        mapper.registerModule(module);
        return  mapper.writeValueAsString(storerepository.findStoreBySarialIsIn(arials));
    }
}
