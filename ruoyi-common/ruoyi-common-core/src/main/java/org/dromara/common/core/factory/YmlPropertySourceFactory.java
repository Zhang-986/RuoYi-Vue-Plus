package org.dromara.common.core.factory;

import org.dromara.common.core.utils.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * yml 配置源工厂
 *
 * @author Lion Li
 */
public class YmlPropertySourceFactory extends DefaultPropertySourceFactory {

    /**
     * 创建属性源
     *
     * 当处理YAML文件时，此方法重写默认行为以使用YamlPropertiesFactoryBean
     * 如果文件不是YAML格式，则委托给父类处理
     *
     * @param name 属性源的名称
     * @param resource 编码资源，包含要加载的配置文件
     * @return 返回一个属性源对象，具体类型取决于加载的配置文件
     * @throws IOException 如果读取资源时发生I/O错误
     */
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String sourceName = resource.getResource().getFilename();
        if (StringUtils.isNotBlank(sourceName) && StringUtils.endsWithAny(sourceName, ".yml", ".yaml")) {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return new PropertiesPropertySource(sourceName, factory.getObject());
        }
        // 父类进行处理，非yaml文件
        return super.createPropertySource(name, resource);
    }

}
