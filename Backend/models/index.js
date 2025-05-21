import { Sequelize } from "sequelize";

const sequelize = new Sequelize("nombre_base_datos", "root", "", {
    host: "localhost",
    dialect: "mysql",
    port: 3306, // usualmente no es necesario, pero puedes agregarlo si cambiaste el puerto
});

export default sequelize;
