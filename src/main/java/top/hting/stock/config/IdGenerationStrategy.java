package top.hting.stock.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Slf4j
public class IdGenerationStrategy extends UUIDGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String id =null;

        try {
            id = BeanUtils.getProperty(object, "id");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(id)) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
        return id;
    }
}