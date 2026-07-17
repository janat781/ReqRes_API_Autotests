package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class UpdateUserResponse {

        private String name;
        private String job;
        private String updatedAt;


        public UpdateUserResponse() {
        }


        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
