import React from 'react';
import { FaCar, FaBed } from 'react-icons/fa';

const IconRenderer = ({ iconName }) => {
    switch (iconName) {
        case 'FaCar':
            return <FaCar />;
        case 'TbBedFilled':
            return <FaBed />;
        default:
            return null;
    }
};

export default IconRenderer;