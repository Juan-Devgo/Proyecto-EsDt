// index.js
import express from "express";
import cors from "cors";
import sequelize from "./models/index.js";
import Publication from "./models/Publication.js";

const app = express();
app.use(cors());

await sequelize.authenticate();
await sequelize.sync();

app.get("/recommended-content", async (req, res) => {
    const content = await Publication.findAll();
    res.send(content);
});

app.get("/recent-publications", async (req, res) => {
    const recent = await Publication.findAll({
        limit: 5, //Esto es para mostrar las últimas 5 publis
        order: [["createdAt", "DESC"]],
    });
    res.send(recent);
});

app.listen(4029, () => console.log("Servidor corriendo en puerto 4029"));