package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.PositionRequest;
import com.ao.portfolio.dto.PositionResponse;
import com.ao.portfolio.entity.Position;
import com.ao.portfolio.exception.ResourceNotFoundException;
import com.ao.portfolio.repository.PositionRepository;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PositionResponse savePosition(PositionRequest request) {
        Position position = new Position();
        position.setSymbol(request.getSymbol());
        position.setQuantity(request.getQuantity());
        position.setAvgPrice(BigDecimal.valueOf(request.getAvgPrice()));

        Position saved = positionRepository.save(position);
        return toResponse(saved);
    }

    public List<PositionResponse> saveAll(List<PositionRequest> requests) {
        List<Position> positions = requests.stream()
                .map(request -> {
                    Position position = new Position();
                    position.setSymbol(request.getSymbol());
                    position.setQuantity(request.getQuantity());
                    position.setAvgPrice(BigDecimal.valueOf(request.getAvgPrice()));
                    return position;
                })
                .collect(Collectors.toList());

        return positionRepository.saveAll(positions)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PositionResponse getPositionById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found: " + id));

        return toResponse(position);
    }

    public Position getEntityById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found: " + id));
    }

    public double calculatePnL(Position position, double currentPrice) {
        BigDecimal currentPriceValue = BigDecimal.valueOf(currentPrice);

        BigDecimal pnl = currentPriceValue
                .subtract(position.getAvgPrice())
                .multiply(BigDecimal.valueOf(position.getQuantity()));

        return pnl.doubleValue();
    }

    private PositionResponse toResponse(Position position) {
        return new PositionResponse(
                position.getId(),
                position.getSymbol(),
                position.getQuantity(),
                position.getAvgPrice().doubleValue()
        );
    }
}