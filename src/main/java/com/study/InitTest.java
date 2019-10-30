package com.study;

import com.study.bean.TestBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

/**
 * @author ：fei
 * @date ：Created in 2019/10/21 0021 10:20
 */
public class InitTest {
    public static void main(String[] args) {
        //资源
        Resource resource = new ClassPathResource("beans.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);
        TestBean testBean = (TestBean) beanFactory.getBean("testBean");
        System.out.println(testBean.getName());
        System.out.println(testBean.getBean());

    }
}
