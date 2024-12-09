package de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
public class MemberExtension {
        @Id
        @GeneratedValue
        protected long id;

        public MemberExtension(String name, String type, boolean isCustomerField) {
                this.name = name;
                this.type = type;
                this.isCustomerField = isCustomerField;
        }

        private String name;
        private String type;

        private Boolean unique = null;
        private Boolean nullable = null;
        private Boolean insertable = null;
        private Boolean updatable = null;
        private Integer length = null;
        private Integer precision = null;
        private Integer scale = null;

        private boolean isCustomerField;


        public MemberExtension() {
        }
}
