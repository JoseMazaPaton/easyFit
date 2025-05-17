import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DeviceDetectorService {
  
  constructor() { }
  
  /**
   * Detecta si el dispositivo actual es un móvil basándose en el user agent
   * @returns boolean - true si el dispositivo es móvil
   */
  isMobile(): boolean {
    const userAgent = window.navigator.userAgent.toLowerCase();
    return /android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini|mobile|tablet/i.test(userAgent);
  }
  
  /**
   * Detecta si el dispositivo actual es una tablet
   * @returns boolean - true si el dispositivo es tablet
   */
  isTablet(): boolean {
    const userAgent = window.navigator.userAgent.toLowerCase();
    return /ipad|tablet/i.test(userAgent);
  }
  
  /**
   * Detecta si el dispositivo actual es un desktop
   * @returns boolean - true si el dispositivo es desktop
   */
  isDesktop(): boolean {
    return !this.isMobile() && !this.isTablet();
  }
  
  /**
   * Obtiene el ancho de pantalla actual
   * @returns number - ancho de pantalla en píxeles
   */
  getScreenWidth(): number {
    return window.innerWidth;
  }
  
  /**
   * Obtiene la altura de pantalla actual
   * @returns number - altura de pantalla en píxeles
   */
  getScreenHeight(): number {
    return window.innerHeight;
  }
} 