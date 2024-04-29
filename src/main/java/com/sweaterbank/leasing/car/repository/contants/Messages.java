package com.sweaterbank.leasing.car.repository.contants;

public class Messages {

    public static final String REJECT_MAIL_MESSAGE =
            """
                Dear %s %s,
                    
                We regret to inform you that your recent car lease application (%s) with Sweatbank has been declined.
                
                While we cannot provide specific details here, the decision was based on various factors including creditworthiness and financial history.
                
                We understand this news may be disappointing. If you have questions or need further assistance, please don't hesitate to reach out.
                
                Thank you for considering Sweatbank.
                
                Best regards,
                
                Sweatbank, +370 5 111 111 | info@sweatbank.com
            """;

    public static final String APPROVED_MAIL_MESSAGE =
            """
               Dear %s %s,
                   
               We are delighted to inform you that your recent car lease application (%s) has been approved! We are thrilled to assist you in acquiring the perfect vehicle for your needs.
               
               Here are the details of your approved lease:
               
                   Lease Amount: %s
                   Margin: %s
                   Interest base: %s
                   Lease Term: %s
               
               Should you have any questions or require further clarification, please do not hesitate to contact our dedicated customer support team. We are here to provide assistance throughout the process.
               
               Once again, congratulations on your approved car lease! We look forward to facilitating your journey with your new vehicle.
               
               Best regards,
               
               Sweatbank, +370 5 111 111 | info@sweatbank.com
            """;
}
