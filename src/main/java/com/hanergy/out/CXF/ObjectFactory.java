
package com.hanergy.out.CXF;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cpic.webservice.cxf package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Exception_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "Exception");
    private final static QName _AddReview_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "addReview");
    private final static QName _AddReviewResponse_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "addReviewResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cpic.webservice.cxf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link java.lang.Exception }
     * 
     */
    public java.lang.Exception createException() {
        return new java.lang.Exception();
    }

    /**
     * Create an instance of {@link AddReview }
     * 
     */
    public AddReview createAddReview() {
        return new AddReview();
    }

    /**
     * Create an instance of {@link AddReviewResponse }
     * 
     */
    public AddReviewResponse createAddReviewResponse() {
        return new AddReviewResponse();
    }

    /**
     * Create an instance of {@link KmReviewParamterForm }
     * 
     */
    public KmReviewParamterForm createKmReviewParamterForm() {
        return new KmReviewParamterForm();
    }

    /**
     * Create an instance of {@link AttachmentForm }
     * 
     */
    public AttachmentForm createAttachmentForm() {
        return new AttachmentForm();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link java.lang.Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "Exception")
    public JAXBElement<java.lang.Exception> createException(java.lang.Exception value) {
        return new JAXBElement<java.lang.Exception>(_Exception_QNAME, java.lang.Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddReview }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "addReview")
    public JAXBElement<AddReview> createAddReview(AddReview value) {
        return new JAXBElement<AddReview>(_AddReview_QNAME, AddReview.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddReviewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "addReviewResponse")
    public JAXBElement<AddReviewResponse> createAddReviewResponse(AddReviewResponse value) {
        return new JAXBElement<AddReviewResponse>(_AddReviewResponse_QNAME, AddReviewResponse.class, null, value);
    }

}
