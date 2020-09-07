package GAJI;

import GAJI.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCanceled_CancelPayment(@Payload Canceled canceled){

        if(canceled.isMe()){
            Optional<Payment> paymentOptional = paymentRepository.findById(canceled.getId());
            Payment payment = paymentOptional.get();
            payment.setStatus("Payment Cancelled");
            payment.setProductId(canceled.getProductId());

            paymentRepository.save(payment);
        }
    }

}
