package hibrnate.validator.error;

/**
 * Created by eladw on 2/4/2015.
 */
public enum RejectCodes {

        WRONG_PARAMETER("Wrong parameter"),
        GENERAL("General error")
        ;

        private String code;

        RejectCodes(String code) {
            this.code = code;
        };

        public String getCode(){
            return code;
        }

}
