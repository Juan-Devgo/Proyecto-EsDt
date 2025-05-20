import express from "express";
import cors from "cors";

const app = express();
app.use(cors());

// Mock data for recent publications
const recentPublications = [
    {
        id: 1,
        title: "Título Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
    {
        id: 2,
        title: "Título Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
    {
        id: 3,
        title: "Título Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
    {
        id: 4,
        title: "Título Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
];

// Mock data for recommended content
const recommendedContent = [
    {
        id: 1,
        title: "Titulo Publicación angie",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
        id: 2,
        title: "Titulo Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
        id: 3,
        title: "Titulo Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
        id: 4,
        title: "Titulo Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
        id: 5,
        title: "Titulo Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
        id: 6,
        title: "Titulo Publicación",
        author: "Nombre de Usuario",
        image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
];

app.get("/recommended-content", (req, res) => {
    res.send(recommendedContent)
});

app.get("/recent-publications", (req, res) => {
    res.send(recentPublications)
});


app.listen(4029, () => console.log("app is running"));
