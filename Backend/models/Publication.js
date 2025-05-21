import { DataTypes } from "sequelize";
import sequelize from "./index.js";

const Publication = sequelize.define("Publication", {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
    },
    title: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    author: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    authorId: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    image: {
        type: DataTypes.STRING(500),
        allowNull: true,
    },
    content: {
        type: DataTypes.TEXT,
        allowNull: true,
    },
    createdAt: {
        type: DataTypes.DATE,
        allowNull: false,
        defaultValue: DataTypes.NOW,
    },
    tags: {
        type: DataTypes.JSON,
        allowNull: true,
    },
    likes: {
        type: DataTypes.INTEGER,
        allowNull: true,
        defaultValue: 0,
    },
    comments: {
        type: DataTypes.INTEGER,
        allowNull: true,
        defaultValue: 0,
    },
    isBookmarked: {
        type: DataTypes.BOOLEAN,
        allowNull: true,
        defaultValue: false,
    },
}, {
    timestamps: false,
});

export default Publication;
