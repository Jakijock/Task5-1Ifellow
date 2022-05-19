package regresIn.Models;

public class UserDTO {

        public String name;
        public String job;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public UserDTO(String name, String job) {
            this.name = name;
            this.job = job;

        }
    }
